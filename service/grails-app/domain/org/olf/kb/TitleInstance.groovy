package org.olf.kb

import javax.persistence.Transient
import org.hibernate.sql.JoinType
import org.olf.erm.Entitlement
import com.k_int.web.toolkit.refdata.RefdataValue
import com.k_int.web.toolkit.refdata.Defaults
import java.time.LocalDate

import grails.gorm.MultiTenant

/**
 * mod-erm representation of a BIBFRAME instance
 */
public class TitleInstance extends ErmResource implements MultiTenant<TitleInstance> {
  
  static namedQueries = {
    entitled {
      createAlias ('platformInstances', 'pi')
      createAlias ('pi.packageOccurences', 'pi_po', JoinType.LEFT_OUTER_JOIN)
      createAlias ('pi_po.pkg', 'pi_po_pkg', JoinType.LEFT_OUTER_JOIN)
      or {
        isNotEmpty 'pi.entitlements'
        isNotEmpty 'pi_po.entitlements'
        isNotEmpty 'pi_po_pkg.entitlements'
      }
    }
  }
  
  // For grouping sibling title instances together - EG Print and Electronic editions of the same thing
  Work work
  
  // Journal/Book/...
  @Defaults(['Journal', 'Book'])
  RefdataValue type

  // Print/Electronic
  @Defaults(['Print', 'Electronic'])
  RefdataValue subType

  LocalDate dateMonographPublishedPrint
  LocalDate dateMonographPublishedOnline
  
  String firstAuthor

  String monographEdition
  String monographVolume

  static hasMany = [
    identifiers: IdentifierOccurrence,
    platformInstances: PlatformTitleInstance
  ]

  static mappedBy = [
    identifiers: 'title',
    platformInstances: 'titleInstance'
  ]

  static mapping = {
                          work column:'ti_work_fk'
                          type column:'ti_type_fk'
                       subType column:'ti_subtype_fk'
   dateMonographPublishedPrint column: 'ti_date_monograph_published_print'
  dateMonographPublishedOnline column: 'ti_date_monograph_published_online'
                   firstAuthor column: 'ti_first_author'
              monographEdition column: 'ti_monograph_edition'
               monographVolume column: 'ti_monographVolume'
  }

  static constraints = {
            name (nullable:false, blank:false)
            work (nullable:true, blank:false)

  }

  public String getCodexSummary() {
    return "${this}";
  }
  
  public String toString() {
    "${name} (${type?.value}/${subType?.value})"
  }
}
