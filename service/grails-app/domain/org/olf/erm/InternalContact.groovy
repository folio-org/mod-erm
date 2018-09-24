package org.olf.erm

import grails.gorm.MultiTenant

public class InternalContact implements MultiTenant<InternalContact>{
	
	String id
	String lastName
	String firstName
	String role
	
	static belongsTo = [
		owner: SubscriptionAgreement
	]

    static mapping = {
		table 'internal_contact'
                   id column: 'ic_id', generator: 'uuid', length:36
              version column: 'ic_version'
                owner column: 'ic_owner_fk'
			 lastName column: 'ic_last_name'
			firstName column: 'ic_first_name'
			     role column: 'ic_role'
  }

  static constraints = {
	  id(nullable:false, blank:false);
	  owner(nullable:false, blank:false);
	  lastName(nullable:true, blank:false);
	  firstName(nullable:true, blank:false);
	  role(nullable:true, blank:false);
  }
}
