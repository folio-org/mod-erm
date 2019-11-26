package org.olf.general

import groovy.transform.CompileStatic
import org.springframework.web.multipart.MultipartFile

@CompileStatic
class FileUploadDataService {
  FileUpload save(MultipartFile file) {
    
    // Create our object to house our file data.
    FileObject fobject = new FileObject ()
    fobject.fileContents = file
    
    FileUpload fileUpload = new FileUpload()
    fileUpload.fileContentType = file.contentType
    fileUpload.fileName = file.originalFilename
    fileUpload.fileSize = file.size
    fileUpload.fileObject = fobject
    
    fileUpload.save()
    fileUpload
  }
  
}
