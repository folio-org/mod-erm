package org.olf.general

import java.time.Instant

import grails.gorm.MultiTenant

class LogEntry implements MultiTenant<LogEntry> {
  String id
  String message
  Instant dateCreated
  String origin
  String detail

  static mapping = {
              id column: 'le_id', generator: 'uuid2', length:36
         message column: 'le_message'
     dateCreated column: 'le_datecreated'
          origin column: 'le_origin'
          detail column: 'le_detail'
  }
  static constraints = {
         message(nullable:true, blank:false)
     dateCreated(nullable:true, blank:false)
          origin(nullable:true, blank:false)
          detail(nullable:true, blank:false)
  }
}

