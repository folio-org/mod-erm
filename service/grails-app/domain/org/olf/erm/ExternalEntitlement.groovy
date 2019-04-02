package org.olf.erm

import com.k_int.okapi.remote_resources.OkapiLookup
import grails.gorm.MultiTenant

/** 
 * Grails control object for external endpoint
 */
class ExternalEntitlement extends Entitlement implements MultiTenant<ExternalEntitlement> {
  // These three properties allow us to create an entitlement which is externally defined. An externally defined
  // entitlement does not link to a resource in the tenant database, but instead will use API calls to define
  // the contents of a package. An example would be Authority:'eHoldings', reference: '301-3707', label 'Bentham science complete
  // as defined in EKB'
  String authority
  
  @OkapiLookup(
    value = '${obj.authority?.toLowerCase() == "ekb-package" ? "/eholdings/packages" : "/eholdings/titles" }/${obj.reference}',
    converter = {
      def map = [
        label: it.data?.attributes?.name,
        type: (it.data?.type?.replaceAll(/^\s*([\S])(.*)s\s*$/, {match, String firstChar, String nonePlural -> "${firstChar.toUpperCase()}${nonePlural}"})),
        provider: it.data?.attributes?.providerName
      ]
      def count = it.data?.attributes?.titleCount
      if (count) {
        map.titleCount = count
      }
      map
    }
  )
  String reference
  
  static mapping = {
            authority column: 'ent_authority'
            reference column: 'ent_reference'
                
         // Keeps the previous data correct.
         discriminator value: 'external'
  }
  
  static constraints = {
    authority (nullable:false)
    reference (nullable:false)
    
    // For external resources we want a null value for the "resource"
    resource (nullable:true, validator: { val, inst -> val != null ? ['externalEntitlement.resource'] : true})    
  }
}
