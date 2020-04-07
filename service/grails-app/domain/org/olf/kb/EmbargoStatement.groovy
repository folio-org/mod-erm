package org.olf.kb

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import java.time.Period
import java.time.temporal.ChronoUnit

@Slf4j
@EqualsAndHashCode(includes=['type', 'length', 'unit'])
class EmbargoStatement {
  // Expose this enum.
  public final enum Type {
    P,
    R
  }
  
  public final enum Unit {
    D ('Days'), M ('Months'), Y ('Years')
    
    private String fullname;
    private Unit(String fullname) {
        this.fullname = fullname;
    }
   
    @Override
    public String toString(){
        return fullname;
    }
  }
  
  Type type
  Unit unit
  int length
  
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
  
  public static final REGEX = /(P|R)(\d+)(D|M|Y)/
  public static final EmbargoStatement parse( String embargoStatement ) {
    log.info "Attempting to parse ${embargoStatement} as EmbargoStatement"
    def match = embargoStatement ? embargoStatement.toUpperCase() =~ "^${REGEX}\$" : null
    
    // Fail fast if null or invalid formatting.
    if (!match) {
      log.info "Failing fast and returning null"
      return null
    }
    
    // Matched.
    EmbargoStatement stmt = new EmbargoStatement(
      type:   Type.valueOf(match[0][1]),
      length: match[0][2] as Integer,
      unit:   Unit.valueOf(match[0][3])
    )
    
    
    log.info "Returning ${stmt}"
    stmt
  }
  
  String toString() {
    "${type}${length}${unit.name}"
  }
}
