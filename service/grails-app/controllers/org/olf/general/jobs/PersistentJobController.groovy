package org.olf.general.jobs


import com.k_int.okapi.OkapiTenantAwareController

import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j


@Slf4j
@CurrentTenant
class PersistentJobController extends OkapiTenantAwareController<PersistentJob> {

  // Read only. DOesn't allow posts etc.
  public PersistentJobController() {
    super(PersistentJob, true);
  }
}
