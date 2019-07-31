package org.olf.export
import groovy.transform.AutoClone
import java.lang.reflect.Field

@AutoClone(excludes = ['date_first_issue_online', 'date_last_issue_online'])
public class KBart {
	
  KBart() {
	//	
  }

  public String title_id = "" // not implemented
  public String publication_title = "" // TitleInstance.name
  public String title_url = "" // platformTitleInstance.url
  public String print_identifier = "" // titleInstance.identifiers.value WHERE .ns = "issn" OR ??? WHERE .ns = "isbn"
  public String online_identifier = "" // titleInstance.identifiers.value WHERE .ns = "eissn"
  public String date_first_issue_online = "" // derived from coverageService
  public String num_first_vol_online = "" // not implemented
  public String num_first_issue_online = "" // not implemented
  public String date_last_issue_online = "" // derived from coverageService
  public String num_last_vol_online = "" // not implemented
  public String num_last_issue_online = "" // not implemented
   
  public String first_author = "" // not implemented
  public String embargo_info = "" // not implemented
  public String coverage_depth = "" // platformContentItem.depth
  public String notes = "" // platformContentItem.note
  public String publisher_name = "" // ????
  public String publication_type  = "" // TitleInstance.type
  public String date_monograph_published_print = "" // not implemented
  public String date_monograph_published_online = "" // not implemented
  public String monograph_volume = "" // not implemented
  public String monograph_edition = "" // not implemented
  public String first_editor = "" // not implemented
  public String parent_publication_title_id = "" // not implemented
  public String preceding_publication_title_id = "" // not implemented
  public String access_type = "" // not implemented	
	
  public String toString() {
	final String separator = "\t"
	String s = "" +
	this.title_id + separator +
	this.publication_title + separator +
	this.title_url + separator +
	this.print_identifier + separator +
	this.online_identifier + separator +
	this.date_first_issue_online + separator +
	this.num_first_vol_online + separator +
	this.num_first_vol_online + separator +
	this.date_last_issue_online + separator +
	this.num_last_vol_online + separator +
	this.num_last_vol_online + separator +
	this.first_author + separator +
	this.embargo_info + separator +
	this.coverage_depth + separator +
	this.notes + separator +
	this.publisher_name + separator +
	this.publication_type + separator +
	this.date_monograph_published_print + separator +
	this.date_monograph_published_online + separator +
	this.monograph_edition + separator +
	this.first_editor + separator +
	this.parent_publication_title_id + separator +
	this.preceding_publication_title_id + separator +
	this.access_type
			
	return s
  }
	
	 
	
  public Map asMap() {
	this.class.declaredFields.findAll { !it.synthetic }.collectEntries {
	  [ (it.name):this."$it.name" ]
	}
  }

}
