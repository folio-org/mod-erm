package org.olf.general.jobs

import org.openqa.selenium.logging.LogEntries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.TransactionDefinition

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import grails.gorm.multitenancy.Tenants
import java.time.Instant

public class JobAwareAppender extends AppenderBase<ILoggingEvent> {
  
  @Override
  protected void append(final ILoggingEvent eventObject) {
    try {
      final Serializable jid = JobRunnerService.jobContext.get()?.jobId
      if (jid) {
        switch (eventObject.level) {
          case Level.INFO:
          case Level.ERROR:
          case Level.WARN:
          
            final Serializable tid = JobRunnerService.jobContext.get()?.tenantId
            JobLoggingService.handleLogEvent(tid, jid, eventObject.message, eventObject.level.levelStr, Instant.ofEpochMilli(eventObject.timeStamp))
          break
        }
      }
    } catch (Throwable t) { 
      t.printStackTrace()
    }
  }
}