package org.olf

import org.olf.dataimport.internal.PackageContentImpl
import org.olf.dataimport.internal.PackageSchema.ContentItemSchema
import org.olf.kb.KBCacheUpdater
import org.olf.kb.RemoteKB
import org.olf.kb.TitleInstance

import grails.gorm.transactions.Transactional

class TitleEnricherService {
  

  public void secondaryEnrichment(RemoteKB kb, String sourceIdentifier, String ermIdentifier) {
    log.debug("TitleEnricherService::enrichTitle called for title with source identifier: ${sourceIdentifier}, erm identifier: ${ermIdentifier} and RemoteKb: ${kb.name}")
    // Only bother continuing if the source is trusted for TI metadata
    if (kb.trustedSourceTI) {
      Class cls = Class.forName(kb.type)
      KBCacheUpdater cache_updater = cls.newInstance()
      // If this KB doesn't require a secondary enrichment call then we can exit here.
      if (cache_updater.requiresSecondaryEnrichmentCall()) {

        TitleInstance ti = TitleInstance.findById(ermIdentifier)
        if (ti) {
          log.debug("LOGDEBUG TI: ${ti}")
          
          //TODO Also check job id against ermIdentifier somehow to double check not running this twice
          Map titleInstanceEnrichmentValues = cache_updater.getTitleInstance(kb.name, kb.uri, sourceIdentifier, ti?.type?.value, ti?.subType?.value)
          log.debug("LOGDEBUG TIEV: ${titleInstanceEnrichmentValues}")
        } else {
          log.error("Could not find ti with id: ${ermIdentifier}, skipping secondary enrichment call.")
        }
      }
    }
  }
}