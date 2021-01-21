package org.olf

import org.olf.erm.SubscriptionAgreement;

import static groovy.transform.TypeCheckingMode.SKIP
import groovy.transform.CompileStatic

@CompileStatic
class SubscriptionAgreementCleanupService {



  @CompileStatic(SKIP)
  private List<List<String>> batchFetchAgreements(final int agreementBatchSize, int agreementBatchCount) {
    // Fetch the ids and localCodes for all platforms
    List<List<String>> agreements = SubscriptionAgreement.createCriteria().list ([max: agreementBatchSize, offset: agreementBatchSize * agreementBatchCount]) {
      order 'id'
      projections {
        property('id')
        property('startDate')
        property('endDate')
        property('periods')
      }
    }
    return agreements
  }

  void triggerDateCleanup() {
    
  }
}
