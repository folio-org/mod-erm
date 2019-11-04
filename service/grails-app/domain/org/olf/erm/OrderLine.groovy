package org.olf.erm;

import com.k_int.okapi.remote_resources.RemoteOkapiLink
import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue

import grails.gorm.MultiTenant

public class OrderLine extends RemoteOkapiLink implements MultiTenant<OrderLine> {
  
	static belongsTo = [ owner: Entitlement ]
  
	static mapping = {
	                  owner column: 'pol_owner_fk'                  
	}
  
	static constraints = {
		owner(nullable:false, blank:false)
	}

	@Override
	public String remoteUri() {
		return 'orders-storage/po-lines';
	}
}
