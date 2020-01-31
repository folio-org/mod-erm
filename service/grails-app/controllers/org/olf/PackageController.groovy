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

import org.olf.dataimport.erm.CoverageStatement

@Slf4j
@CurrentTenant
class PackageController extends OkapiTenantAwareController<Pkg> {
  
  ImportService importService

  PackageController() {
    super(Pkg)
  }

  def 'import' () {
    final bindObj = this.getObjectToBind()
    log.debug("bindObj: ${bindObj}")
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
      monograph_edition: [field: 'monographEdition', index: -1],
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

    log.debug("ValuesList: ${acceptedFields.values()}")
    log.debug("Finding title bit ${acceptedFields.values().find { it.field.equals('title') }}")

    //TODO Don't do this all in one go, do work line by line
    

    String[] record;
    while ((record = csvReader.readNext()) != null) {
        for (String value : record) {
          def lineAsArray = value.split("\t")
          // Currently just prints out each line as an array
          log.debug("Line: ${lineAsArray}")

          LocalDate startDate
          if (getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.startDate')) {
            startDate = LocalDate.parse(getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.startDate'))
          }

          LocalDate endDate
          if (getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.endDate')) {
            endDate = LocalDate.parse(getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.endDate'))
          }

          Map envelope = [
            // TODO fill this header out properly
            header :[
              packageSource: '123.456',
              packageName: 'myPackage',
              packageSlug: '123456789'
            ],
            packageContents: [
              [
                title: getFieldFromLine(lineAsArray, acceptedFields, 'title'),
                siblingInstanceIdentifiers: getFieldFromLine(lineAsArray, acceptedFields, 'siblingInstanceIdentifiers'),
                instanceIdentifiers: getFieldFromLine(lineAsArray, acceptedFields, 'instanceIdentifiers'),
                coverage: new CoverageStatement(
                  startDate: startDate,
                  startVolume: getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.startVolume'),
                  startIssue: getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.startIssue'),
                  endDate: endDate,
                  endVolume: getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.endVolume'),
                  endIssue: getFieldFromLine(lineAsArray, acceptedFields, 'CoverageStatement.endIssue')
                ),
                url: getFieldFromLine(lineAsArray, acceptedFields, 'url'),
                firstAuthor: getFieldFromLine(lineAsArray, acceptedFields, 'firstAuthor'),
                embargo: getFieldFromLine(lineAsArray, acceptedFields, 'embargo'),
                coverageDepth: getFieldFromLine(lineAsArray, acceptedFields, 'coverageDepth'),
                coverageNote: getFieldFromLine(lineAsArray, acceptedFields, 'coverageNote'),
                // TODO work out what's happening with the two types here

                //TitleInstance.type: getFieldFromLine(lineAsArray, acceptedFields, 'TitleInstance.type')
                dateMonographPublished: getFieldFromLine(lineAsArray, acceptedFields, 'dateMonographPublished'),
                monographVolume: getFieldFromLine(lineAsArray, acceptedFields, 'monographVolume'),
                monographEdition: getFieldFromLine(lineAsArray, acceptedFields, 'monographEdition'),
                firstEditor: getFieldFromLine(lineAsArray, acceptedFields, 'firstEditor'),
              ]
            ]
          ]
          importService.importPackageUsingInternalSchema(envelope)
        }
    }    
    return render (status: 200)
    render [:] as JSON;
  }

  private getFieldFromLine(String[] lineAsArray, Map acceptedFields, String fieldName) {
    return lineAsArray[(acceptedFields.values().find { it.field.equals(fieldName) })['index']];
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

