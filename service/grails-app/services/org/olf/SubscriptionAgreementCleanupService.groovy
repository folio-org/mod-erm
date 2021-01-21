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

  private List<List<String>> fetchPeriodsForAgreement(final String agreementId) {
    List<List<String>> periods = Period.createCriteria().list() {
      eq('owner.id', agreementId)
      projections {
        property('startDate')
        property('endDate')
      }
    }
    return periods
  }

  void triggerDateCleanup() {
    final int agreementBatchSize = 100
    int agreementBatchCount = 0
    SubscriptionAgreement.withNewTransaction {
      List<List<String>> agreements = batchFetchAgreements(agreementBatchSize, agreementBatchCount)
      while (agreements && agreements.size() > 0) {
        agreementBatchCount++
        agreements.each { a ->
          List<List<String>> periods = fetchPeriodsForAgreement(a[0])
          LocalDate earliest = null
          for (def p : periods) {
            if (earliest == null || p[0] < earliest) earliest = p[0]
          }
          LocalDate latest = null
          for (def p : periods) {
            if(p[1] == null) {
              latest = null
              break
            } else if (latest == null || p[1] > latest) {
              latest = p[1]
            }
          }
         
         if (a[1] != earliest || a[2] != latest) {
           print("LOGDEBUG MISMATCH! AGREEMENT START(${a[1]}), EARLIEST PERIOD START(${earliest})")
           print("LOGDEBUG MISMATCH! AGREEMENT START(${a[2]}), EARLIEST PERIOD START(${latest})")
         }
        }
        
        // Next page
        agreements = batchFetchAgreements(agreementBatchSize, agreementBatchCount)
      }
    }
  }
}
