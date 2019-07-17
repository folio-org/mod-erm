package org.olf.general.jobs

import java.time.Instant

import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

class LogEntry implements MultiTenant<LogEntry> {
  
  String id
  String type
  
  public static final String TYPE_ERROR='error'
  public static final String TYPE_INFO='info'
  
  String message
  Instant dateCreated = Instant.now()
  String origin
  
  static mapping = {
              id column: 'le_id', generator: 'uuid2', length:36
         message column: 'le_message', type: 'text'
     dateCreated column: 'le_datecreated'
            type column: 'le_type'
          origin column: 'le_origin', index: 'origin_idx'
  }
  static constraints = {
         message (nullable:true, blank:false)
     dateCreated (nullable:true, blank:false)
          origin (nullable:false, blank:false)
            type (nullable:false, blank:false)
  }
}

