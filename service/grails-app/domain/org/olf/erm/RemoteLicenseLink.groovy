package org.olf.erm;

import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

public class RemoteLicenseLink implements MultiTenant<RemoteLicenseLink> {
  String id
  
  @Defaults(['Controlling', 'Future', 'Historical'])
  RefdataValue status
  
  String note
  
  static mapping = {
             id column:'rll_id', generator: 'uuid', length:36
        version column:'rll_version'
         status column:'rll_status'
           note column:'rll_note', type: 'text'
  }
  
  static constraints = {
          licenseType (nullable:false)
                 note (nullable:true, blank:false)
  }
}
