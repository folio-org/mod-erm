databaseChangeLog = {
  changeSet(author: "claudia (manual)", id: "202004061829-01") {
        createTable(tableName: "alternate_name") {
            column(name: "an_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "an_name", type: "VARCHAR(255)")
        }
  }

  changeSet(author: "claudia (manual)", id: "202004061829-02") {
        addForeignKeyConstraint(baseColumnNames: "an_id", baseTableName: "alternate_name", constraintName: "an_to_sa_fk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "sa_id", referencedTableName: "subscription_agreement")
  }
}
