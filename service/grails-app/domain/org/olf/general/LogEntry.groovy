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
              id column: 'el_id', generator: 'uuid2', length:36
         message column: 'el_message'
     dateCreated column: 'el_datecreated'
          origin column: 'el_origin'
          detail column: 'el_detail'
  }
  static constraints = {
         message(nullable:true, blank:false)
     dateCreated(nullable:true, blank:false)
          origin(nullable:true, blank:false)
          detail(nullable:true, blank:false)
  }
}

