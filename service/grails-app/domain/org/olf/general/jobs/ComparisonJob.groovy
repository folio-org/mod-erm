package org.olf.general.jobs

import org.olf.erm.ComparisonPoint

import grails.gorm.MultiTenant

class ComparisonJob extends PersistentJob implements MultiTenant<ComparisonJob>{

  Set<ComparisonPoint> comparisonPoints = []
  
  static hasMany = [
    comparisonPoints: ComparisonPoint
  ]
  
  final Closure work = {
    log.info "Run the comparison"
  }
  
  static constraints = {
    comparisonPoints (minSize: 2, nullable: false)
  }
}
