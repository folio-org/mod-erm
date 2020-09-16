package org.olf.general.jobs

import grails.gorm.MultiTenant
import grails.gorm.multitenancy.Tenants

class SupplementaryPropertiesCleaningJob extends PersistentJob implements MultiTenant<SupplementaryPropertiesCleaningJob>{
  String schemaName = ""

  final Closure work = {
    log.info "Running Supplementary Properties Cleaning Job"
    documentAttachmentService.triggerCleanSuppDocs(schemaName)
  }
}
