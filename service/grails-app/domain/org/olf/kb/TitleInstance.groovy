package org.olf.kb

import javax.persistence.Transient

import org.olf.erm.Entitlement
import org.olf.general.RefdataValue
import org.olf.general.refdata.Defaults

import grails.gorm.MultiTenant

/**
 * mod-erm representation of a BIBFRAME instance
 */
public class TitleInstance extends ErmResource implements MultiTenant<TitleInstance> {

  // For grouping sibling title instances together - EG Print and Electronic editions of the same thing
  Work work
  
  // Journal/Book/...
  @Defaults(['Journal', 'Book'])
  RefdataValue type

  // Print/Electronic
  @Defaults(['Print', 'Electronic'])
  RefdataValue subType

  static mapping = {
             work column:'ti_work_fk'
  }

  static constraints = {
            name (nullable:false, blank:false)
            work (nullable:true, blank:false)
  }

  static hasMany = [
    identifiers: IdentifierOccurrence,
    platformInstances: PlatformTitleInstance
  ]

  static mappedBy = [
    identifiers: 'title',
    platformInstances: 'titleInstance'
  ]
}
