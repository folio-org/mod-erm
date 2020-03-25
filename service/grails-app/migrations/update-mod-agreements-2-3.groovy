databaseChangeLog = {
  changeSet(author: "efreestone (manual)", id: "202003231555-1") {
      modifyDataType( 
        tableName: "custom_property_definition", 
        columnName: "pd_description", type: "text",
        newDataType: "text",
        confirm: "Successfully updated the pd_description column."
      )
  }
  changeSet(author: "efreestone (manual)", id: "202003250914-1") {
    addColumn(tableName: "remotekb") {
      column(name: "rkb_trusted_source_ti", type: "boolean")
    }
  }
  // Set all remote KBs to not-trusted
  changeSet(author: "efreestone (manual)", id: "202003250914-2") {
    grailsChange {
      change {
        sql.execute("UPDATE ${database.defaultSchemaName}.remotekb SET rkb_trusted_source_ti=false".toString())
      }
    }
  }
  // Set LOCAL to trusted
  changeSet(author: "efreestone (manual)", id: "202003250914-3") {
    grailsChange {
      change {
        sql.execute("UPDATE ${database.defaultSchemaName}.remotekb SET rkb_trusted_source_ti=true WHERE rkb_name='LOCAL'".toString())
      }
    }
  }
  // Add non-nullable constraint
  changeSet(author: "efreestone (manual)", id: "202003250914-4") {
    addNotNullConstraint(tableName: "remotekb", columnName: "rkb_trusted_source_ti", columnDataType: "boolean".toString())
  }

}
