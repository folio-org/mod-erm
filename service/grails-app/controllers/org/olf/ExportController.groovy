package org.olf

import com.k_int.okapi.OkapiTenantAwareController
 
import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j 
import grails.converters.JSON
import org.olf.kb.TitleInstance
import org.olf.kb.PlatformTitleInstance
import org.olf.kb.IdentifierOccurrence
import org.olf.kb.Identifier
import org.olf.kb.IdentifierNamespace
import org.olf.kb.CoverageStatement
import org.olf.kb.Platform
import org.olf.kb.ErmResource
import org.olf.export.KBart
import static org.springframework.http.HttpStatus.OK

/**
 * The ExportController provides endpoints for exporting content in specific formats
 * harvested by the erm module.
 */
@Slf4j
@CurrentTenant
class ExportController extends OkapiTenantAwareController<TitleInstance>  {
	
	
  ExportService exportService
	
  final String csvMimeType = 'text/csv'
  final String tsvMimeType = 'text/tab-separated-value'
  final String encoding = "UTF-8"
  

  ExportController()  {
	  super(TitleInstance, true)
  }
	
  /**
   * main index method (by default, return titles as json)
   */
  def index() {
      log.debug("ExportController::index");
	  
	  final String subscriptionAgreementId = params.get("subscriptionAgreementId")
	  println "id: "+subscriptionAgreementId
	  
	  List<ErmResource> results
	  if (subscriptionAgreementId) {
		  log.debug("Getting export for specific agreement: "+ subscriptionAgreementId)
		  results = exportService.entitled()
		  log.debug("found this many resources: "+ results.size())
		  //mapToKBart(results)
		  
	  } else {
		  log.debug("Getting export for all agreements")
	      results = exportService.entitled()
		  log.debug("found this many resources: "+ results.size())
		  
		 // mapToKBart(results)

	  }
	  
	   
	  respond results 
  }

  /*
   * kbart export (placeholder)
   */
  def kbart() {

	  final String filename = 'export.tsv' 
	  String headline = exportService.kbartheader()
	  withFormat {
		  tsv { 
			  response.status = OK.value()
			  response.contentType = "${tsvMimeType};charset=${encoding}";
			  response.setHeader "Content-disposition", "attachment; filename=${filename}"
			  def out = response.outputStream
			  out.withWriter {
					  writer -> writer.write(headline)
					  writer.flush()
					  writer.close()
			  }
			  out.flush()
		  }
	  }
	  
	   
  }
  
  public void mapToKBart(final List<ErmResource> resources) {
	  for (ErmResource res: resources) {
		  
		PlatformTitleInstance pti = res.pti
		TitleInstance ti = pti.titleInstance
		
		KBart kbart = new KBart()
	    //println res.getClass().getName()
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
		println kbart.toString() 
		
		//println kbart as JSON
	  }
  }
  
   

}

