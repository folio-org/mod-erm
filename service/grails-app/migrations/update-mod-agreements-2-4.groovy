databaseChangeLog = {
    changeSet(author: "claudia (manual)", id: "202006291630-1") {
        addColumn(tableName: "subscription_agreement_org") {
            column(name: "sao_note", type: "text")
        }
    }
    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-1") {
      createTable(tableName: "comparison_job") {
        column(name: "id", type: "VARCHAR(255)") {
          constraints(nullable: "false")
        }
      }

      addPrimaryKey(columnNames: "id", constraintName: "comparison_jobPK", tableName: "comparison_job")
    }

    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-2") {
      createTable(tableName: "comparison_job_erm_title_list") {
        column(name: "comparison_job_title_lists_id", type: "VARCHAR(255)") {
          constraints(nullable: "false")
        }

        column(name: "erm_title_list_id", type: "VARCHAR(36)")
      }
    }

    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-3") {
      createTable(tableName: "erm_title_list") {
        column(name: "id", type: "VARCHAR(36)") {
          constraints(nullable: "false")
        }

        column(name: "version", type: "BIGINT") {
          constraints(nullable: "false")
        }
      }
    
      addPrimaryKey(columnNames: "id", constraintName: "erm_title_listPK", tableName: "erm_title_list")
    }

    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-4") {
      addForeignKeyConstraint(baseColumnNames: "comparison_job_title_lists_id", baseTableName: "comparison_job_erm_title_list", constraintName: "FKefnd56sd714idn8w9mk0d74s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "comparison_job")
    }

    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-5") {
      addForeignKeyConstraint(baseColumnNames: "erm_title_list_id", baseTableName: "comparison_job_erm_title_list", constraintName: "FKf2ssfsku9ncr10lfdqg2mv435", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "erm_title_list")
    }
    
    chengeSet(author: "sosguthorpe", id: "1593002234734-6") {
      // Copy all agreement ID/versions into title list table
      grailsChange {
        change {
          sql.execute("""
            INSERT INTO ${database.defaultSchemaName}.erm_title_list(id, version)
            SELECT sa_id, sa_version FROM ${database.defaultSchemaName}.subscription_agreement;
          """.toString())
        }
      }
      
      dropColumn(columnName: "sa_version", tableName: "subscription_agreement")
    }
    
    
    chengeSet(author: "sosguthorpe", id: "1593002234734-7") {
      // Copy all resource ID/versions into title list table
      grailsChange {
        change {
          sql.execute("""
            INSERT INTO ${database.defaultSchemaName}.erm_title_list(id, version)
            SELECT id, version FROM ${database.defaultSchemaName}.erm_resource;
          """.toString())
        }
      }
      dropColumn(columnName: "version", tableName: "erm_resource")
    }
    
    
    /** Tidy missing PK contraint **/
    changeSet(author: "sosguthorpe (generated)", id: "1593002234734-8") {
      addPrimaryKey(columnNames: "id", constraintName: "kbart_import_jobPK", tableName: "kbart_import_job")
    }
}