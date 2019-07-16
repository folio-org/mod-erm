package org.olf
import org.olf.kb.ErmResource
import org.olf.kb.PackageContentItem
import org.olf.kb.Pkg
import org.olf.kb.TitleInstance 

import com.k_int.okapi.OkapiTenantAwareController

import grails.gorm.DetachedCriteria
import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j
import grails.gorm.transactions.Transactional 
import org.hibernate.Hibernate
import org.hibernate.sql.JoinType 
 

// uncomment these when code for ERM-215 is ready
//import org.olf.export.KBart
//import org.olf.export.KBartExport



/**
 * 
 */
@Transactional
public class ExportService { 
	CoverageService coverageService
	
   List<ErmResource> entitled() { 
	  // Ian: It's now possible for an agreement to have entitlements that do not link to a resource. Need
      // to talk through with steve about how this should work.
      final def results = ErmResource.createCriteria ().list {
        or {
          
          // Direct relations...
          // Use a sub query to avoid multiple entries per item.
          'in' 'id', new DetachedCriteria(ErmResource).build {
            
            createAlias 'entitlements', 'direct_ent'
              ne 'class', Pkg
              isNotNull 'direct_ent.owner.id'
            
            projections {
              property ('id')
            }
          }
          
          
          // Pci linked via package.
          'in' 'id', new DetachedCriteria(PackageContentItem).build {
        
            'in' 'pkg.id', new DetachedCriteria(Pkg).build {
              createAlias 'entitlements', 'pkg_ent'
                isNotNull 'pkg_ent.owner.id'
                
                projections {
                  property ('id')
                }
            }
            
            projections {
              property ('id')
            }
          }
        }
        
        readOnly (true)
      }
      
      // This method writes to the web request if there is one (which of course there should be as we are in a controller method)
      coverageService.lookupCoverageOverrides(results)
      
     
	  return results 
  }
  
  String kbartheader() {
	  /*
	   The export should include:
	  
	  Agreement name (desirable, not required)
	  Agreement ID (desirable, not required)
	  Title
	  Title Identifiers (all known identifiers for a title including ISSN, eISSN, EZB, ZDB,...)
	  Coverage information for a title (start date, start volume, start issue, end date, end volume, end issue) (note that a single title can have multiple coverage statements, all coverage information should be included in the JSON)
	  URL for title in agreement
	  Publisher
	  E-resource type
	  Platform
	  Package
	  */ 

      String headline =
      "publication_title\t" +
      "print_identifier\t" +
      "online_identifier\t" +
      "date_first_issue_online\t" +
      "num_first_vol_online\t" +
      "num_first_issue_online\t" +
      "date_last_issue_online\t" +
      "num_last_vol_online\t" +
      "num_last_issue_online\t" +
      "title_url\t" +
      "first_author\t" +
      "title_id\t" +
      "embargo_info\t" +
      "coverage_depth\t" +
      "notes\t" +
      "publisher_name\t" +
      "publication_type\t" +
      "date_monograph_published_print\t" +
      "date_monograph_published_online\t" +
      "monograph_volume\t" +
      "monograph_edition\t" +
      "first_editor\t" +
      "parent_publication_title_id\t" +
      "preceding_publication_title_id\t" +
      "access_type\t";
       
	  
	  return headline

	  
      /*def fieldsNames = new StringBuilder() 
	   
	  def fields = KBart.declaredFields
	  fields.each {
		 fieldsNames << it.name << "\t"
	  } 
	  return fieldsNames.toString()*/
	  
  }

}
