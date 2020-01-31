package org.olf

import org.olf.kb.PackageContentItem
import org.olf.kb.Pkg

import com.k_int.okapi.OkapiTenantAwareController
import grails.converters.JSON
import grails.gorm.multitenancy.CurrentTenant
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import java.time.LocalDate

import org.springframework.web.multipart.MultipartFile
import org.apache.commons.io.input.BOMInputStream

import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder

@Slf4j
@CurrentTenant
class PackageController extends OkapiTenantAwareController<Pkg> {
  
  ImportService importService

  PackageController() {
    super(Pkg)
  }

  def 'import' () {
    final bindObj = this.getObjectToBind()
    importService.importPackageUsingErmSchema(bindObj as Map)
    return render (status: 200)
  }
  
  def 'tsv_parse' () {

    MultipartFile file = request.getFile('upload')

    BOMInputStream bis = new BOMInputStream(file.getInputStream());
    Reader fr = new InputStreamReader(bis);
    CSVReader csvReader = new CSVReaderBuilder(fr).build();

    // peek gets line without removing from iterator
    // readNext gets line and removes it from the csvReader object
    String headerValue = csvReader.readNext()[0]
    def header = (headerValue).split("\t")

    // Create an object containing fields we can accept and their mappings in our domain structure, as well as indices in the imported file, with -1 if not found
    Map acceptedFields = [
      publication_title: [field: 'title', index: -1],
      print_identifier: [field: 'siblingInstanceIdentifiers', index: -1],
      online_identifier: [field: 'instanceIdentifiers', index: -1],
      date_first_issue_online: [field: 'CoverageStatement.startDate', index: -1],
      num_first_vol_online: [field: 'CoverageStatement.startVolume', index: -1],
      num_first_issue_online: [field: 'CoverageStatement.startIssue', index: -1],
      date_last_issue_online: [field: 'CoverageStatement.endDate', index: -1],
      num_last_vol_online: [field: 'CoverageStatement.endVolume', index: -1],
      num_last_issue_online: [field: 'CoverageStatement.endIssue', index: -1],
      title_url: [field: 'url', index: -1],
      first_author: [field: 'firstAuthor', index: -1],
      title_id: [field: null, index: -1],
      embargo_info: [field: 'embargo', index: -1],
      coverage_depth: [field: 'coverageDepth', index: -1],
      notes: [field: 'coverageNote', index: -1],
      publisher_name: [field: null, index: -1],
      publication_type: [field: 'TitleInstance.type', index: -1],
      date_monograph_published_print: [field: 'dateMonographPublished', index: -1],
      date_monograph_published_online: [field: 'dateMonographPublished', index: -1],
      monograph_volume: [field: 'monographVolume', index: -1],
      monograph_edition: [field: 'monographVolume', index: -1],
      first_editor: [field: 'firstEditor', index: -1],
      parent_publication_title_id: [field: null, index: -1],
      preceding_publication_title_id: [field: null, index: -1],
      access_type : [field: null, index: -1]
    ]

    // Map each key to its location in the header
    for (int i=0; i<header.length; i++) {
      final String key = header[i]
      if (acceptedFields.containsKey(key)) {
        acceptedFields[key]['index'] = i
      }
    }

    //TODO Don't do this all in one go, do work line by line
    

    String[] record;
    while ((record = csvReader.readNext()) != null) {
        for (String value : record) {
          // Currently just prints out each line as an array
          log.debug("Line: ${value.split("\t")}")
        }
    }
      

    log.debug("Accepted Fields: ${acceptedFields}")
    

    render [:] as JSON;
  }

  def content () {
    respond doTheLookup(PackageContentItem) {
      eq 'pkg.id', params.'packageId'
      isNull 'removedTimestamp'
    }
  }
  
  def currentContent () {
    final LocalDate today = LocalDate.now()
    respond doTheLookup(PackageContentItem) {
      eq 'pkg.id', params.'packageId'
      and {
        or {
          isNull 'accessEnd'
          gte 'accessEnd', today
        }
        or {
          isNull 'accessStart'
          lte 'accessStart', today
        }
      }
      isNull 'removedTimestamp'
    }
  }
  
  def futureContent () {
    final LocalDate today = LocalDate.now()
    respond doTheLookup(PackageContentItem) {
      eq 'pkg.id', params.'packageId'
      gt 'accessStart', today
      isNull 'removedTimestamp'
    }
  }
  
  def droppedContent () {
    final LocalDate today = LocalDate.now()
    respond doTheLookup(PackageContentItem) {
      eq 'pkg.id', params.'packageId'
      lt 'accessEnd', today
      isNull 'removedTimestamp'
    }
  }
}

