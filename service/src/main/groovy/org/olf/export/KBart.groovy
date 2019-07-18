package org.olf.export

import java.lang.reflect.Field

public class KBart {
	
   KBart() {
		
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
		String s = ""+
		"title: "+ this.publication_title + "\n" +
		"online_identifier: "+this.online_identifier + "\n" +
		"print_identifier: "+this.print_identifier + "\n" +
		"start_date: "+this.date_first_issue_online + "\n" +
		"end_date: "+this.date_last_issue_online + "\n" +
		"title_url: "+this.title_url + "\n" +
		"depth: "+this.coverage_depth + "\n" +
		"notes: "+this.notes + "\n" +
		"publication_type: "+this.publication_type + "\n" 
	}
	
	 
	
	public Map asMap() {
		this.class.declaredFields.findAll { !it.synthetic }.collectEntries {
		  [ (it.name):this."$it.name" ]
		}
	}

}
