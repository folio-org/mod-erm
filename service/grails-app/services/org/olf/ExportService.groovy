package org.olf
import org.olf.erm.Entitlement
import org.olf.kb.ErmResource
import org.olf.kb.PackageContentItem
import org.olf.kb.Pkg 
import org.olf.kb.TitleInstance
import org.olf.kb.PlatformTitleInstance
import org.olf.kb.IdentifierOccurrence
import org.olf.kb.Identifier
import org.olf.kb.IdentifierNamespace
import org.olf.kb.CoverageStatement
import org.olf.kb.Platform 

import com.k_int.okapi.OkapiTenantAwareController

import grails.gorm.DetachedCriteria
import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j
import grails.gorm.transactions.Transactional 
import org.hibernate.Hibernate
import org.hibernate.sql.JoinType 
 

// uncomment these when code for ERM-215 is ready
import org.olf.export.KBart
import org.olf.export.KBartExport



/**
 * 
 */
@Transactional
public class ExportService { 
	CoverageService coverageService
	
   List<ErmResource> entitled() { 
	  // Ian: It's now possible for an agreement to have entitlements that do not link to a resource. Need
      // to talk through with steve about how this should work.
      final def results = ErmResource.executeQuery("""

        SELECT res, pkg_ent, direct_ent
        FROM ErmResource as res
          LEFT JOIN res.entitlements as direct_ent
          LEFT JOIN res.pkg as pkg
            ON res.class = PackageContentItem
            LEFT JOIN pkg.entitlements as pkg_ent
        WHERE
          (
            direct_ent.owner IS NOT NULL
            AND
            res.class != Pkg
          )
        OR
          (
            pkg_ent.owner IS NOT NULL
          )
      """, [readOnly: true])
      
      // At this point we should have a List of results. But instead of each result being an ErmResource we should have a collection
      // of [0]->ErmResource, [1]->Entitlement, [2]->Entitlement.
      
      // The first entitlement will be present if this is a PCI resource and it associated through a package and the second will be
      // present if this resource was directly associated to an entitlement. This means that can/will get multiple entries for the
      // same resource if there are multiple packages, or if the resources is associated directly and also through a packge to an 
      // agreement. This behaviour is actually desirable for the export. 
      
      // This method writes to the web request if there is one (which of course there should be as we are in a controller method)
      coverageService.lookupCoverageOverrides(results.collect{it[0]}) 
      
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

      // get headers as a map 

	  KBart kbart = new KBart()
      Map map = kbart.asMap()
	  StringWriter sw = new StringWriter() 
	  map.each{ k, v -> sw.append("${k}" + "\t") }
	  return sw.toString()
	  
	  
  }
  
  public List<KBart> mapToKBart(final List<Object> resources) {
	  log.debug("map to kbart")
	  List<KBart> kbartList = new ArrayList<KBart>();
	  for (Object res: resources) {
		
		  
		if (res instanceof Entitlement) {
		    log.debug("this is an entititlement")
		} else if (res instanceof ErmResource) {
			log.debug("this is an ermResource")
		} else {
			log.debug("resource class: "+res.getClass().getName())
		}
		
		
		/*PlatformTitleInstance pti = res.pti
		TitleInstance ti = pti.titleInstance
		
		KBart kbart = new KBart()
		
		//kbart.publication_title = res.name
		if (res.depth) kbart.coverage_depth = res.depth
		if (res.note) kbart.notes = res.note
		if (pti) {
		  //println "found pti"
		  if (pti.url) kbart.title_url = pti.url
		}
		
		if (ti) {
			//println "found titleInstance"
			kbart.publication_title = ti.name
			if (ti.type.value) kbart.publication_type = ti.type.value
			Object obj = ti.identifiers
			if (obj) {
				//println "got ti.identifiers: "+ obj.getClass().getName()
				Iterator iter = ti.identifiers.iterator();
				while (iter.hasNext()) {
					IdentifierOccurrence thisIdent = iter.next()
					Identifier ident =  thisIdent.identifier
					if (ident) {
						if (ident.ns.value.equals("eissn")) {
							kbart.online_identifier = ident.value
						} else if (ident.ns.value.equals("isbn")) {
							kbart.print_identifier = ident.value
						} else if (ident.ns.value.equals("issn")) {
							kbart.print_identifier = ident.value
						}
						 
							
					}
				}
			}
			println "\n"
		}
		kbartList.add(kbart)
		println kbart.toString()*/
	     
		
	  }
	  return kbartList
  }
  
  

}
