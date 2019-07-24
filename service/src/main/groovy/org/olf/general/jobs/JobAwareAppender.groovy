package org.olf.general.jobs

import org.openqa.selenium.logging.LogEntries
import org.springframework.transaction.TransactionDefinition

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import grails.gorm.multitenancy.Tenants
import java.time.Instant

public class JobAwareAppender extends AppenderBase<ILoggingEvent> {
  
  private final Closure addLogEntry = { final ILoggingEvent eventObject, final Serializable jobId ->
    
    LogEntry.withNewTransaction {
        LogEntry le = new LogEntry(
          type: eventObject.level.levelStr,
          origin: jobId,
          message: eventObject.message,
          dateCreated: Instant.ofEpochMilli(eventObject.timeStamp)
        )
        
        le.save(failOnError: true)
    }
  }

  @Override
  protected void append(final ILoggingEvent eventObject) {
    try {
      final Serializable jid = JobRunnerService.jobContext.get()?.jobId
      if (jid) {
        switch (eventObject.level) {
          case Level.INFO:
          case Level.ERROR:
          
            final Serializable tid = JobRunnerService.jobContext.get()?.tenantId
          
            if (tid) {
              Tenants.withId( tid, addLogEntry.curry(eventObject, jid) )
            } else {
              addLogEntry(eventObject, jid)
            }
          break
        }
      }
    } catch (Throwable t) { /* Silent */ }
  }
}