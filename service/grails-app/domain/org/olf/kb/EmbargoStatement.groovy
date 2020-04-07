package org.olf.kb

import java.time.Period
import java.time.temporal.ChronoUnit

class EmbargoStatement {
  // Expose this enum.
  public final enum Type {
    P,
    R
  }
  
  public final enum Unit {
    D (ChronoUnit.DAYS),
    M (ChronoUnit.MONTHS),
    Y (ChronoUnit.YEARS)
  }
  
  Type type
  Unit unit
  int length
  
  /**
   * Even though this statement uses the ISO-8601 to define its syntax.
   * It embeds meaning into the Unit part. This method will allow us to store
   * the real offset in time, and retain the original value.
   */
  Period period
  
  static mapping = {
    id column:'est_id', generator: 'uuid2', length:36
    type column:'est_type'
    unit column:'est_unit'
    length column:'est_length'
  }
  
  static constraints = {
        type (nullable:false)
        unit (nullable:false)
      length (nullable:false, min: 1)
  }
  Period toPeriod () {
    
  }
}
