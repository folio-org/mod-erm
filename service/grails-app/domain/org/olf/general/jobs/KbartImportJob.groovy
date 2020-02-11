package org.olf.general.jobs

import grails.gorm.MultiTenant

import org.springframework.web.multipart.MultipartFile
import org.apache.commons.io.input.BOMInputStream

import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder

class KbartImportJob extends PersistentJob implements MultiTenant<KbartImportJob> {
  
  /* private final MultipartFile file = request.getFile('upload')

    Map packageInfo = [
      packageName: request.getParameter("packageName"),
      packageSource: request.getParameter("packageSource"),
      packageReference: request.getParameter("packageReference")
    ]
    
    BOMInputStream bis = new BOMInputStream(file.getInputStream());
    Reader fr = new InputStreamReader(bis);
    CSVReader csvReader = new CSVReaderBuilder(fr).build();
    

    def completed = importService.importPackageFromKbart(csvReader, packageInfo)
   */


  final Closure getWork() {
    
    final Closure theWork = { final String eventId, final String tenantId ->
    
      log.info "Running KBART Package Import Job"
      PersistentJob.withTransaction {
      
        // We should ensure the job is read into the current session. This closure will probably execute
        // in a future session and we need to reread the event in.
        final PersistentJob job = PersistentJob.read(eventId)
        log.debug("What even is a job?: ${job}")
        if (job.fileUpload) {

          BOMInputStream bis = new BOMInputStream(job.fileUpload.fileObject.getInputStream())
          Reader fr = new InputStreamReader(bis);
          CSVReader csvReader = new CSVReaderBuilder(fr).build();

          //importService.importFromFile(js.parse( job.fileUpload.fileObject.fileContents.binaryStream ))
        } else {
          log.error "No file attached to the Job."
        }
      }
    }.curry( this.id )
    
    theWork
  }
  
  void beforeValidate() {
    if (!this.name && this.fileUpload) {
      // Set the name from the file upload if no name has been set.
      this.name = "Import package from ${this.fileUpload.fileName}"
    }
  }
  
  static constraints = {
      fileUpload (nullable:false)
  }
}
