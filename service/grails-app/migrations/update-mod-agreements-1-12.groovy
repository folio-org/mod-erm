databaseChangeLog = {
  // Add new column for Reason for Closure
  changeSet(author: "ethanfreestone (manual)", id: "260920190932-1") {
    addColumn(tableName: "subscription_agreement") {
      column(name: "sa_reason_for_closure", type: "VARCHAR(36)") {
        constraints(nullable: "true")
      }
    }
    addForeignKeyConstraint(baseColumnNames: "sa_reason_for_closure", baseTableName: "subscription_agreement", constraintName: "reasonforclosureforeignkeyconstraint26092019", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
  }

  // Set Foreign Key constraints
  changeSet(author: "ethanfreestone (manual)", id: "260920190932-2") {
    grailsChange {
      change {
        // Add new refdata category for reason for closure
        sql.execute("UPDATE ${database}.refdata_category SET rdc_description = COALESCE(SubscriptionAgreement.ReasonForClosure);".toString())

        // Add cancelled to the refdatavalues table as a default option
        sql.execute("UPDATE ${database.defaultSchemaName}.refdata_value SET sa_reason_for_closure = COALESCE(cancelled);".toString())

        // Add closed to the refdatavalues table as a default option
        sql.execute("UPDATE ${database.defaultSchemaName}.refdata_value SET sa_agreement_status = COALESCE(closed);".toString())

        // Change each entry with status 'cancelled' to have reason_for_closure 'cancelled'
        sql.execute("UPDATE ${database}.subscription_agreement SET sa_reason_for_closure = cancelled WHERE sa_agreement_status = cancelled")

        // Finally change each entry with status 'cancelled' to status 'closed'
        sql.execute("UPDATE ${database}.subscription_agreement SET sa_agreement_status = closed WHERE sa_agreement_status = cancelled")
      }
    }
  }
}