package org.olf.kb.converters

import org.olf.kb.Embargo

import grails.databinding.converters.ValueConverter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
class EmbargoConverter implements ValueConverter {
  
  @Override
  boolean canConvert(Object value) {
    value instanceof String
  }

  @Override
  Object convert(Object value) {
    log.info "Attempting to convert ${value} to an embargo"
    Embargo.parse(value as String)
  }

  @Override
  Class<?> getTargetType() {
    Embargo
  }
}
