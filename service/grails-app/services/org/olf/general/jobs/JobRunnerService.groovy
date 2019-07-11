package org.olf.general.jobs

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import javax.annotation.PostConstruct

import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.datastore.mapping.multitenancy.MultiTenantCapableDatastore
import org.grails.orm.hibernate.HibernateDatastore

import grails.events.EventPublisher
import grails.events.annotation.Subscriber
import groovy.util.logging.Slf4j

@Slf4j
class JobRunnerService implements EventPublisher {
  int globalConcurrentJobs = 3 // We need to be careful to not completely tie up all our resource
  
  private ExecutorService executorSvc
  
  @PostConstruct
  void init() {
    // Set up the Executor
    executorSvc = Executors.newFixedThreadPool(globalConcurrentJobs)
    
    new ThreadPoolExecutor(
      3,  // Core pool Idle threads.
      3, // 100 threads max.
      1000, // 1 second wait.
      TimeUnit.MILLISECONDS, // Makes the above wait time in 'seconds'
      new LinkedBlockingQueue<Runnable>() // Use a synchronous queue
    )
    
    // Get the list of jobs from all tenants that were interrupted by app termination and
    // set their states appropriately.
    
    // Rebuild a queue from all tenants.
    populateJobQueue()
    
    // Raise an event to say we are ready.
    notify('jobs:job_runner_ready')
  }
  
  @Subscriber
  void onPostInsert(PostInsertEvent event) {
    log.info 'onPostInsert()'
    if (PersistentJob.class.isAssignableFrom(event.entity.javaClass)) {
      final def source = event.source
      HibernateDatastore k
      if (MultiTenantCapableDatastore.class.isAssignableFrom(source.class)) {
        MultiTenantCapableDatastore mt_source = source
        // Rasie job created event.
        notify('jobs:job_created')
      }
    }
  }
  
  @Subscriber
  void onJobCreated(final String jobId, final String tenantId) {
    // Attempt to append to queue.
    log.info 'onJobCreated()'
    enqueueJob()
  }
  
  void getInterruptedJobs() {
    
  }
  
  void populateJobQueue() {
    
  }
  
  void enqueueJob() {
    
  }  
}