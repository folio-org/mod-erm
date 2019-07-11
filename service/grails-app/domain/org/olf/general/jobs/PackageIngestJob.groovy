package org.olf.general.jobs

import grails.gorm.MultiTenant

class PackageIngestJob extends PersistentJob implements MultiTenant<PackageIngestJob>{

  Closure work = {
    log.info "Running Package Ingest Job"
  }
}
