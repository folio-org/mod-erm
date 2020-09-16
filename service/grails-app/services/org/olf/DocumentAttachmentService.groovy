package org.olf

import com.github.zafarkhaja.semver.Version
import com.github.zafarkhaja.semver.ParseException

import grails.events.annotation.Subscriber
import grails.gorm.multitenancy.Tenants
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import groovy.sql.Sql

import com.k_int.okapi.OkapiTenantResolver

import org.olf.general.DocumentAttachment
import org.olf.general.jobs.SupplementaryPropertiesCleaningJob

@Slf4j
public class DocumentAttachmentService {

  def dataSource

  private static final Version SUPP_DOCS_DUPLICATES_VERSION = Version.forIntegers(3) // Version trigger.

  @Subscriber('okapi:tenant_enabled')
  public void onTenantEnabled (final String tenantId, final boolean existing_tenant, final boolean upgrading, final String toVersion, final String fromVersion) {
    if (upgrading && fromVersion) {
      try {
        if (Version.valueOf(fromVersion).compareTo(SUPP_DOCS_DUPLICATES_VERSION) <= 0) {
          // We are upgrading from a version prior to when the supplementary document duplication was fixed,
          // lets schedule a job to retrospectively separate those duplicates out
          log.debug "Clean supplementary document duplicates based on tenant upgrade prior to fix being present"
          triggerCleanSuppDocsForTenant(tenantId)
        }
      } catch(ParseException pex) {
        // From version couldn't be parsed as semver we should ignore.
        log.debug "${fromVersion} could not be parsed as semver, not running supplementary document clean."
      }
    }
  }

  @Subscriber('okapi:tenant_clean_supplementary_docs')
  public void onTenantCleanSupplementaryDocs(final String tenantId, final String value, final String existing_tenant, final String upgrading, final String toVersion, final String fromVersion) {
    // We want to explicitly schedule a job to retrospectively separate duplicate supplementary documents out
    log.debug "Clean supplementary document duplicates based on explicit request during tenant activation"
    triggerCleanSuppDocsForTenant(tenantId)
  }

  private void triggerCleanSuppDocsForTenant(final String tenantId) {
    log.debug "LOGDEBUG triggered clean supp docs"
    final String tenant_schema_id = OkapiTenantResolver.getTenantSchemaName(tenantId)
    Tenants.withId(tenant_schema_id) {

      SupplementaryPropertiesCleaningJob job = SupplementaryPropertiesCleaningJob.findByStatusInList([
        SupplementaryPropertiesCleaningJob.lookupStatus('Queued'),
        SupplementaryPropertiesCleaningJob.lookupStatus('In progress')
      ])

      if (!job) {
        job = new SupplementaryPropertiesCleaningJob(name: "Supplementary Document Cleanup ${Instant.now()}", schemaName: tenant_schema_id)
        job.setStatusFromString('Queued')
        job.save(failOnError: true, flush: true)
      } else {
        log.debug('Supplementary document cleaning job already running or scheduled. Ignore.')
      }
    }
  }

  @Transactional
  private void triggerCleanSuppDocs(String schemaName) {
    Sql sql = new Sql(dataSource)

    List nonUniqueSuppDocs = sql.rows("SELECT docs.sasd_da_fk FROM ${schemaName}.subscription_agreement_supp_doc AS docs GROUP BY docs.sasd_da_fk HAVING COUNT(*) > 1".toString())
    nonUniqueSuppDocs.each { suppDoc ->
      DocumentAttachment suppDocDC = DocumentAttachment.findById(suppDoc.sasd_da_fk)
      
      // For each of those docs, return a list of the sa keys they're linked to (Should be more than one per, thanks to the above line)
      List agreementsWithGivenDoc = sql.rows("SELECT docs.sasd_sa_fk FROM ${schemaName}.subscription_agreement_supp_doc as docs WHERE docs.sasd_da_fk = :da_key".toString(), [da_key: suppDoc.sasd_da_fk])
      // At this point we have a list of agreements that a particular supp doc is attached to.
      // While that list is > 1 we should clone the supp_doc and create a new link in the table

      while (agreementsWithGivenDoc.size() > 1) {
        DocumentAttachment suppDocTemp = suppDocDC.clone()
        suppDocTemp.save(flush: true, failOnError: true)

        String saId = agreementsWithGivenDoc[0].sasd_sa_fk

        // Create new link to cloned document
        sql.execute("""
            INSERT INTO ${schemaName}.subscription_agreement_supp_doc (sasd_sa_fk, sasd_da_fk) VALUES (:sa_key,:da_key)
          """.toString(),[sa_key: saId, da_key: suppDocTemp.id])

        // Delete old link to cloned document
        sql.execute("""
          DELETE FROM ${schemaName}.subscription_agreement_supp_doc WHERE sasd_sa_fk = :sa_key AND sasd_da_fk = :da_key
        """.toString(),[sa_key: saId, da_key: suppDoc.sasd_da_fk])

        // Re-assign the list after doing work
        agreementsWithGivenDoc = sql.rows("SELECT docs.sasd_sa_fk FROM ${schemaName}.subscription_agreement_supp_doc as docs WHERE docs.sasd_da_fk = :da_key".toString(), [da_key: suppDoc.sasd_da_fk])
      }
    }
  }

}