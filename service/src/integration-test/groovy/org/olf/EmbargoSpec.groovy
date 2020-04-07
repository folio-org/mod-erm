package org.olf

import grails.testing.mixin.integration.Integration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import spock.lang.Stepwise

@Integration
@Stepwise
class EmbargoSpec extends BaseSpec {
  
  def 'Test embargo parsing' (final String from, final String embargo, final boolean valid, final String startDate, final String endDate) {
    
    where:
      from            | embargo           || valid    | startDate      |   endDate     
      '2018-12-14'    | 'R10Y;P30D'       || true     | '2009-01-01'   |   '2018-11-15'
}

