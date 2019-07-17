package org.olf.general.jobs

import java.time.Instant
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import javax.annotation.PostConstruct
import org.olf.KbHarvestService
import com.k_int.okapi.OkapiTenantAdminService
import com.k_int.web.toolkit.refdata.Defaults
import com.k_int.web.toolkit.refdata.RefdataValue
import grails.events.EventPublisher
import grails.events.annotation.Subscriber
import grails.gorm.multitenancy.Tenants
import groovy.util.logging.Slf4j

@Slf4j
class JobLoggingService {
  
  @Subscriber('jobs:log_error')
  void handleLogError(final String tenantId, final String jobId, final String message) {
    handleLogEvent(tenantId, jobId, message, LogEntry.TYPE_ERROR)
  }
  
  @Subscriber('jobs:log_info')
  void handleLogInfo (final String tenantId, final String jobId, final String message) {
    handleLogEvent(tenantId, jobId, message, LogEntry.TYPE_INFO)
  }
  
  void handleLogEvent ( final String tenantId, final String jobId, final String message, final String type) {
    Tenants.withId(tenantId) {
      LogEntry le = new LogEntry()
      le.type = type
      le.message = message
      le.origin = jobId
      le.save(failOnError:true, flush:true)
    }
  }
}