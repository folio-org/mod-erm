package org.olf.general.jobs

import grails.gorm.MultiTenant
import grails.gorm.multitenancy.Tenants
import org.olf.kb.ErmTitleList

class ComparisonJob extends PersistentJob implements MultiTenant<ComparisonJob>{

  Set<ErmTitleList> titleLists = []
  
  static hasMany = [
    titleLists: ErmTitleList
  ]
  
  final Closure work = {
    log.info "Run the comparison"
  }
  
  static constraints = {
    titleLists (minSize: 2, nullable: false)
  }
}
