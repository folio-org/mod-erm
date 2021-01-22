package org.olf

import java.time.LocalDate

import org.olf.erm.SubscriptionAgreement;
import org.olf.erm.Period;

import groovy.transform.CompileDynamic

@CompileDynamic
class SubscriptionAgreementCleanupService {

  private List<List<String>> batchFetchAgreements(final int agreementBatchSize, int agreementBatchCount) {
    // Fetch the ids and localCodes for all platforms
    List<List<String>> agreements = SubscriptionAgreement.createCriteria().list ([max: agreementBatchSize, offset: agreementBatchSize * agreementBatchCount]) {
      order 'id'
      projections {
        property('id')
        property('startDate')
        property('endDate')
      }
    }
    return agreements
  }

  void triggerDateCleanup() {
    final int agreementBatchSize = 100
    int agreementBatchCount = 0
    SubscriptionAgreement.withNewTransaction {
      List<List<String>> agreements = batchFetchAgreements(agreementBatchSize, agreementBatchCount)
      while (agreements && agreements.size() > 0) {
        agreementBatchCount++
        agreements.each { a ->
          SubscriptionAgreement agg = SubscriptionAgreement.get(a[0])
          LocalDate earliest = agg.calculateStartDate(agg.periods)
          LocalDate latest = agg.calculateEndDate(agg.periods)
          
         if (a[1] != earliest || a[2] != latest) {
           log.warn("Agreement date mismatch for (${a[0]}), calculating new start and end dates")
           agg.startDate = earliest
           agg.endDate = latest
           agg.save(failOnError: true)
         }
        }
        
        // Next page
        agreements = batchFetchAgreements(agreementBatchSize, agreementBatchCount)
      }
    }
  }
}
