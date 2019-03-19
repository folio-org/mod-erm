package org.olf

import org.olf.erm.SubscriptionAgreement
import org.olf.kb.ErmResource
import org.olf.kb.PackageContentItem
import org.olf.kb.PlatformTitleInstance
import org.olf.kb.Pkg
import org.olf.erm.Entitlement

import com.k_int.okapi.OkapiTenantAwareController

import grails.gorm.multitenancy.CurrentTenant
import grails.orm.HibernateCriteriaBuilder
import groovy.util.logging.Slf4j
import javax.persistence.criteria.JoinType
import grails.gorm.DetachedCriteria


/**
 * Control access to subscription agreements.
 * A subscription agreement (SA) is the connection between a set of resources (Which could be packages or individual titles) and a license. 
 * SAs have start dates, end dates and renewal dates. This controller exposes functions for interacting with the list of SAs
 */
@Slf4j
@CurrentTenant
class SubscriptionAgreementController extends OkapiTenantAwareController<SubscriptionAgreement>  {
  
  SubscriptionAgreementController() {
    super(SubscriptionAgreement)
  }
  
  def resources (String subscriptionAgreementId) {
    
    if (subscriptionAgreementId) {
      // The in clause below will freak out is the subquery returns an empty list. So we should test for
      // the entitlements list being empty first.
      //
      // Ian: It's now possible for an agreement to have entitlements that do not link to a resource. Need
      // to talk through with steve about how this should work.
        
      def ptis = new DetachedCriteria(PlatformTitleInstance).build {
        createAlias 'entitlements', 'pti_ent'
          eq 'pti_ent.owner.id', subscriptionAgreementId
        
        projections {
          property ('id')
        }
      }
      
      def pcis = new DetachedCriteria(PackageContentItem).build {
//        final def pkgs = new DetachedCriteria(Pkg, 'packages').build {
//          createAlias 'entitlements', 'pkg_ent'
//            eq 'pkg_ent.owner.id', subscriptionAgreementId
//          
//          projections {
//            property ('id')
//            property ('pkg_ent.id')
//          }
//        }
        
        createAlias 'pkg.entitlements', 'pkg_ent'
          eq 'pkg_ent.owner.id', subscriptionAgreementId
        
//        'in' 'pkg.id', pkgs.select('id')
          
        projections {
          property ('id')
          property ('pkg_ent.id')
        }
      }
      
      // Dedupe in a way that means pagination still works.
      respond doTheLookup (ErmResource) {
        or {
          'in' 'id', ptis.select('id')
          'in' 'id', pcis.select('id')
        }
      }
      return
    }
      
  }

}
