package org.olf;

import org.olf.erm.OrderLine

import com.k_int.okapi.OkapiTenantAwareController

import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j

@Slf4j
@CurrentTenant
class OrderLineController extends OkapiTenantAwareController<OrderLine>  {

  OrderLineController() {
    super(OrderLine, true) // The true makes this read only. No POST/PUT/DELETE
  }
}
