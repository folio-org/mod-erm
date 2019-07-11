package org.olf.general.jobs
import java.time.Instant

import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant
import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class PersistentJob implements MultiTenant<PersistentJob> {
  
  static transients = ['work']
    
  String id
  String name
  
  @Defaults(['Queued', 'In progress', 'Ended'])
  RefdataValue status
  List<LogEntry> logEntries
  Instant dateCreated
  Instant started
  Instant ended
  
  @Defaults(['Successful import', 'Partial import', 'Failed import'])
  RefdataValue result
  
  def beforeValidate() {
    // Set default status here.
    if (!status) {
      setStatusFromString('Queued')
    }
  }
  
  static hasMany = [
    logEntries: LogEntry
  ]
  
  
  static mappedBy = ['logEntries': 'job']
  
  static mapping = {
    tablePerHierarchy false
                   id generator: 'uuid2', length:36
                 name column:'job_name'
               status column:'job_status_fk'
           logEntries cascade: 'all-delete-orphan'
          dateCreated column:'job_date_created'
              started column:'job_started'
                ended column:'job_ended'
               result column:'job_result_fk'
  }

  static constraints = {
            name (nullable:false, blank:false)
          status (nullable:false)
     dateCreated (nullable:true)
         started (nullable:true)
           ended (nullable:true)
          result (nullable:true)
  }
  
  abstract Runnable getWork()
  
  String toString() {
    "${name}"
  }
}
