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

    def importedFileArray = []
    // We take the file and turn it into an array of arrays

    // peek gets line without removing from iterator
    // readNext gets line and removes it from the csvReader object
    String[] record;
    while ((record = csvReader.readNext()) != null) {
        for (String value : record) {
          importedFileArray << value.split("\t")
        }
    }

    log.debug("file as an array: ${importedFileArray}")
    def header = importedFileArray[0]
    
    // Create an object containing fields we can accept and their mappings in our domain structure, as well as indices in the imported file, with -1 if not found
    Map acceptedFields = [
      publication_title: ['title', -1],
      print_identifier: ['siblingInstanceIdentifiers', -1],
      online_identifier: ['instanceIdentifiers', -1],
      date_first_issue_online: ['CoverageStatement.startDate', -1],
      num_first_vol_online: ['CoverageStatement.startVolume', -1],
      num_first_issue_online: ['CoverageStatement.startIssue', -1],
      date_last_issue_online: ['CoverageStatement.endDate', -1],
      num_last_vol_online: ['CoverageStatement.endVolume', -1],
      num_last_issue_online: ['CoverageStatement.endIssue', -1],
      title_url: ['url', -1],
      first_author: ['firstAuthor', -1],
      title_id: [null, -1],
      embargo_info: ['embargo', -1],
      coverage_depth: ['coverageDepth', -1],
      notes: ['coverageNote', -1],
      publisher_name: [null, -1],
      publication_type: ['TitleInstance.type', -1],
      date_monograph_published_print: ['dateMonographPublished', -1],
      date_monograph_published_online: ['dateMonographPublished', -1],
      monograph_volume: ['monographVolume', -1],
      monograph_edition: ['monographVolume', -1],
      first_editor: ['firstEditor', -1],
      parent_publication_title_id: [null, -1],
      preceding_publication_title_id: [null, -1],
      access_type : [null, -1]
    ]

    log.debug("Accepted Fields: ${acceptedFields}")
    log.debug("Accepted Fields: ${acceptedFields.getClass()}")
    log.debug("Accepted Fields: ${acceptedFields.publication_title}")

/*     def fieldIndicesForFile = [:]
    acceptedFields.each{key, value ->
    // Create mapping of field
      int index = -1;
      for (int i=0; i<header.length; i++) {
        if (TYPES[i].equals(carName)) {
            index = i;
            break;
        }
      }
    } */

    

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

