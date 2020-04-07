databaseChangeLog = {
  changeSet(author: "efreestone (manual)", id: "202003231555-1") {
      modifyDataType( 
        tableName: "custom_property_definition", 
        columnName: "pd_description", type: "text",
        newDataType: "text",
        confirm: "Successfully updated the pd_description column."
      )
  }
  changeSet(author: "claudia (manual)", id: "202004061829-01") {
    createTable(tableName: "subscription_agreement_alternate_names") {
        column(name: "subscription_agreement_id", type: "VARCHAR(36)") {
            constraints(nullable: "false")
        }
        column(name: "alternate_names_string", type: "VARCHAR(255)")
    }
  }
  
  changeSet(author: "claudia (manual)", id: "202004061829-02") {
     addForeignKeyConstraint(baseColumnNames: "subscription_agreement_id", baseTableName: "subscription_agreement_alternate_names", constraintName: "FKbwixs452hfe48k069eip5xgx0", referencedColumnNames: "sa_id", referencedTableName: "subscription_agreement")
  }
  
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-1") {
    createTable(tableName: "embargo") {
      column(name: "emb_id", type: "VARCHAR(36)") {
        constraints(nullable: "false")
      }

      column(name: "version", type: "BIGINT") {
        constraints(nullable: "false")
      }

      column(name: "moving_wall_end_id", type: "VARCHAR(36)")

      column(name: "emb_start_fk", type: "VARCHAR(36)")
    }
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-2") {
    createTable(tableName: "embargo_statement") {
      column(name: "est_id", type: "VARCHAR(36)") {
        constraints(nullable: "false")
      }

      column(name: "version", type: "BIGINT") {
        constraints(nullable: "false")
      }

      column(name: "est_type", type: "VARCHAR(255)") {
        constraints(nullable: "false")
      }

      column(name: "est_unit", type: "VARCHAR(255)") {
        constraints(nullable: "false")
      }

      column(name: "est_length", type: "INT") {
        constraints(nullable: "false")
      }
    }
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-3") {
    addColumn(tableName: "package_content_item") {
        column(name: "embargo_id", type: "varchar(36)") {
            constraints(nullable: "false")
        }
    }
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-4") {
    addPrimaryKey(columnNames: "est_id", constraintName: "embargoPK", tableName: "embargo")
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-5") {
    addForeignKeyConstraint(baseColumnNames: "embargo_id", baseTableName: "package_content_item", constraintName: "embargo_FK", referencedColumnNames: "emb_id", referencedTableName: "embargo")
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-6") {
    addForeignKeyConstraint(baseColumnNames: "emb_end_fk", baseTableName: "embargo", constraintName: "emb_end_FK", referencedColumnNames: "est_id", referencedTableName: "embargo_statement")
  }
  changeSet(author: "sosguthorpe (generated)", id: "1586288050585-7") {
    addForeignKeyConstraint(baseColumnNames: "emb_start_fk", baseTableName: "embargo", constraintName: "emb_start_FK", referencedColumnNames: "est_id", referencedTableName: "embargo_statement")
  }
}
