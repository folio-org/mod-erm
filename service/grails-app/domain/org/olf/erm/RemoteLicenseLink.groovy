package org.olf.erm;

import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

public class RemoteLicenseLink implements MultiTenant<RemoteLicenseLink> {
  String id // This is the internal ID of the link and not the ID of the remote license.
  
  String remoteId
  
  @Defaults(['Controlling', 'Future', 'Historical'])
  RefdataValue status
  
  String note
  
  static mapping = {
             id column:'rll_id', generator: 'uuid', length: 36
       remoteId column:'rll_remote_id', length: 50 // UUIDs are 36 chars. Allowing some wriggle-room here..
        version column:'rll_version'
         status column:'rll_status'
           note column:'rll_note', type: 'text'
  }
  
  static constraints = {
             remoteId (nullable:false, blank:false)
               status (nullable:false)
                 note (nullable:true, blank:false)
  }
}
