package org.olf.general
import grails.gorm.MultiTenant
import grails.gorm.multitenancy.Tenants
import java.sql.Blob
import javax.persistence.Lob
import org.hibernate.engine.jdbc.BlobProxy

class FileObject implements MultiTenant<FileObject> {

  String id
  FileUpload fileUpload
  
  static belongsTo = [fileUpload: FileUpload]
  
  @Lob
  Blob fileContents
  
  void setFileContents(InputStream is) {
    fileContents = BlobProxy.generateProxy(is)
  }

  static constraints = {
    fileContents nullable: false
    fileUpload   nullable: false
  }

  static mapping = {
                  id column: 'fo_id', generator: 'uuid2', length: 36
         fileContent column: 'fo_contents'
  }
}
