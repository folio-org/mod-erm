package org.olf

import com.k_int.okapi.OkapiTenantAwareController
 
import grails.gorm.multitenancy.CurrentTenant
import groovy.util.logging.Slf4j 
import grails.converters.JSON
import org.olf.kb.TitleInstance
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
	  def results = exportService.entitled()
	  respond results 
  }

  /*
   * kbart export (placeholder)
   */
  def kbart() {

	  final String filename = 'export.tsv' 
	  String headline = exportService.kbartheader()
	  withFormat {
		  csv { 
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
  
   

}

