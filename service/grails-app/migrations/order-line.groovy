databaseChangeLog = {
	changeSet(author: "claudia (manual)", id: "2019-11-04-00001") {
		createTable(tableName: "order_line") {
			column(name: "rol_id", type: "VARCHAR(36)") {
				constraints(nullable: "false")
			}
			column(name: "rol_version", type: "BIGINT") {
				constraints(nullable: "false")
			}
			column(name: "rol_remote_id", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}
			column(name: "pol_owner_fk", type: "VARCHAR(36)") {
				constraints(nullable: "false")
			}
		}
	  }
	  
	  changeSet(author: "claudia (manual)", id: "2019-11-04-00002") {
		  addPrimaryKey(columnNames: "rol_id", constraintName: "order_linePK", tableName: "order_line")
	  }
	  
	  changeSet(author: "claudia (manual)", id: "2019-11-04-00003") {
		  addForeignKeyConstraint(baseColumnNames: "pol_owner_fk",
									baseTableName: "order_line",
								  constraintName: "pol_to_ent_fk",
								  deferrable: "false", initiallyDeferred: "false",
								  referencedColumnNames: "ent_id", referencedTableName: "entitlement")
	  }
}
