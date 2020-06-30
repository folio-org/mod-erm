package org.olf.general.jobs

import java.sql.Blob

import javax.persistence.Lob

import org.hibernate.engine.jdbc.BlobProxy
import org.olf.ComparisonService
import org.olf.erm.ComparisonPoint

import grails.gorm.MultiTenant

class ComparisonJob extends PersistentJob implements MultiTenant<ComparisonJob>{

  @Lob
  Blob fileContents

  static mapping = {
     fileContents column: 'cj_file_contents', lazy: true
  }
  
  Set<ComparisonPoint> comparisonPoints = []
  
  static hasMany = [
    comparisonPoints: ComparisonPoint
  ]
  
  final Closure getWork() {
    final Closure theWork = { final String jobId, final String tenantId ->
      log.info "Run the comparison"
      ComparisonJob.withTransaction {
        
        ComparisonJob job = ComparisonJob.get(jobId)
        job.fileContents = BlobProxy.generateProxy(new byte[0])
        OutputStream blobOS = job.fileContents.setBinaryStream(1)
        try {
          (comparisonService as ComparisonService).compare(blobOS, comparisonPoints as ComparisonPoint[])
        } finally {
          blobOS.close()
        }
      }
    }.curry( this.id )
    theWork
  }
  
  static constraints = {
    comparisonPoints (minSize: 2, nullable: false)
    fileContents nullable: true, bindable: false
  }
}
