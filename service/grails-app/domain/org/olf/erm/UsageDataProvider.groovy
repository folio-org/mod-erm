package org.olf.erm;

import com.k_int.okapi.remote_resources.RemoteOkapiLink

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

@GrailsCompileStatic
public class UsageDataProvider extends RemoteOkapiLink implements MultiTenant<UsageDataProvider> {

	String usageDataProviderNote
  
	static belongsTo = [ owner: SubscriptionAgreement ]
  
	static mapping = {
	                  owner column: 'udp_owner_fk'
    usageDataProviderNote column: 'udp_note', type: 'text'
                  
	}
  
	static constraints = {
		owner(nullable:false, blank:false)
        usageDataProviderNote(nullable:true, blank:false)
	}

	@Override
	public String remoteUri() {
		return 'usage-data-providers';
	}
}
