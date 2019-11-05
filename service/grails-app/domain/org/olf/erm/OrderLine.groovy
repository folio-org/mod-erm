package org.olf.erm;

import com.k_int.okapi.remote_resources.RemoteOkapiLink
import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

public class OrderLine implements MultiTenant<OrderLine> {
	
	String id
	String poLineId
	
	static belongsTo = [ owner: Entitlement ]
  
	  static mapping = {
  //    table 'order_lines'
					 id column: 'pol_id', generator: 'uuid2', length:36
				version column: 'pol_version'
				  owner column: 'pol_owner_fk'  
			   poLineId column: 'pol_orders_fk'
				   
	}
  
	static constraints = {
		 owner(nullable:false, blank:false);
	   poLineId(nullable:true, blank:false);
	}  
}
