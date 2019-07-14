package org.olf.general.jobs

import java.time.Instant

import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

class LogEntry implements MultiTenant<LogEntry> {
  
  String id
  
  @Defaults(['Info', 'Error'])
  RefdataValue type
  
  PersistentJob job
  
  String message
  Instant dateCreated
  String origin
  
  String getOrigin() {
    if (!origin && job) {
      return "${job}"
    }
    origin
  }

  static mapping = {
              id column: 'le_id', generator: 'uuid2', length:36
         message column: 'le_message', type: 'text'
     dateCreated column: 'le_datecreated'
            type column: 'le_type_fk'
             job column: 'le_job_fk'
          origin column: 'le_origin', index: 'origin_idx'
  }
  static constraints = {
         message (nullable:true, blank:false)
     dateCreated (nullable:true, blank:false)
          origin (nullable:true, blank:false)
            type (nullable:false)
             job (nullable:true)
  }
}

