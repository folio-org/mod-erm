databaseChangeLog = {
  changeSet(author: "John Fereira", id: "2019-05-14-ERM-218-4") {
      modifyDataType(tableName: "custom_property_definition", columnName: "pd_description", newDataType: "TEXT")
  }

  changeSet(author: "John Fereira", id: "2019-05-14-ERM-218-5") {
      modifyDataType( 
        tableName: "erm_resource", 
        columnName: "res_description", 
        newDataType: "TEXT",
        confirm: "Successfully updated the res_description column."
      )
  }

  changeSet(author: "John Fereira", id: "2019-05-14-ERM-218-6") {
      modifyDataType( 
        tableName: "subscription_agreement", 
        columnName: "sa_description", 
        newDataType: "TEXT", 
        confirm: "Successfully updated the sa_description column."
      )
  }

  changeSet(author: "John Fereira", id: "2019-05-14-ERM-218-7") {
      modifyDataType( 
         tableName: "refdata_category", 
         columnName: "rdc_description", 
         newDataType: "TEXT", 
         confirm: "Successfully updated the rdc_description column." 
      )
  }
}
