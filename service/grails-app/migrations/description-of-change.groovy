databaseChangeLog = {

    changeSet(author: "malzer (generated)", id: "1537784660621-1") {
        createTable(tableName: "all_electronic_resources") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "plat_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pti_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pkg_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-2") {
        createTable(tableName: "coverage_statement") {
            column(name: "cs_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "cs_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "cs_start_date", type: "VARCHAR(255)")

            column(name: "cs_start_issue", type: "VARCHAR(255)")

            column(name: "cs_pci_fk", type: "VARCHAR(36)")

            column(name: "cs_ti_fk", type: "VARCHAR(36)")

            column(name: "cs_end_issue", type: "VARCHAR(255)")

            column(name: "cs_start_volume", type: "VARCHAR(255)")

            column(name: "cs_end_volume", type: "VARCHAR(255)")

            column(name: "cs_pti_fk", type: "VARCHAR(36)")

            column(name: "cs_end_date", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-3") {
        createTable(tableName: "entitlement") {
            column(name: "ent_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "ent_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ent_pci_fk", type: "VARCHAR(36)")

            column(name: "ent_active_to", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "ent_owner_fk", type: "VARCHAR(36)")

            column(name: "ent_active_from", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "ent_pti_fk", type: "VARCHAR(36)")

            column(name: "ent_pkg_fk", type: "VARCHAR(36)")

            column(name: "ent_enabled", type: "BOOLEAN")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-4") {
        createTable(tableName: "holdings_coverage") {
            column(name: "co_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "co_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "co_start_date", type: "VARCHAR(255)")

            column(name: "co_start_issue", type: "VARCHAR(255)")

            column(name: "co_end_issue", type: "VARCHAR(255)")

            column(name: "co_start_volume", type: "VARCHAR(255)")

            column(name: "co_end_volume", type: "VARCHAR(255)")

            column(name: "co_end_date", type: "VARCHAR(255)")

            column(name: "co_ent_fk", type: "VARCHAR(36)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-5") {
        createTable(tableName: "identifier") {
            column(name: "id_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "id_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "id_ns_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "id_value", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-6") {
        createTable(tableName: "identifier_namespace") {
            column(name: "idns_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "idns_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "idns_value", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-7") {
        createTable(tableName: "identifier_occurrence") {
            column(name: "io_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "io_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "io_ti_fk", type: "VARCHAR(36)")

            column(name: "io_status_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "io_identifier_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-8") {
        createTable(tableName: "internal_contact") {
            column(name: "ic_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "ic_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ic_first_name", type: "VARCHAR(255)")

            column(name: "ic_role", type: "VARCHAR(255)")

            column(name: "ic_owner_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "ic_last_name", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-9") {
        createTable(tableName: "node") {
            column(name: "nd_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "nd_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "nd_reference_class", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "nd_node_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "nd_reference_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "nd_label", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "nd_parent", type: "VARCHAR(36)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-10") {
        createTable(tableName: "org") {
            column(name: "org_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "org_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "org_vendors_uuid", type: "VARCHAR(255)")

            column(name: "org_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "org_source_uri", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-11") {
        createTable(tableName: "package") {
            column(name: "pkg_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pkg_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pkg_remote_kb", type: "VARCHAR(36)")

            column(name: "pkg_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "pkg_vendor_fk", type: "VARCHAR(36)")

            column(name: "pkg_nominal_platform_fk", type: "VARCHAR(36)")

            column(name: "pkg_source", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "pkg_reference", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-12") {
        createTable(tableName: "package_content_item") {
            column(name: "pci_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pci_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pci_access_start", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "pci_depth", type: "VARCHAR(255)")

            column(name: "pci_pti_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pci_pkg_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pci_added_ts", type: "BIGINT")

            column(name: "pci_access_end", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "pci_last_seen_ts", type: "BIGINT")

            column(name: "pci_note", type: "VARCHAR(255)")

            column(name: "pci_removed_ts", type: "BIGINT")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-13") {
        createTable(tableName: "platform") {
            column(name: "pt_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pt_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pt_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-14") {
        createTable(tableName: "platform_locator") {
            column(name: "pl_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pl_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pl_owner_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "domain_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-15") {
        createTable(tableName: "platform_title_instance") {
            column(name: "pti_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pti_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pti_ti_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "pti_url", type: "VARCHAR(255)")

            column(name: "pti_pt_fk", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-16") {
        createTable(tableName: "refdata_category") {
            column(name: "rdc_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "rdc_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rdc_description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-17") {
        createTable(tableName: "refdata_value") {
            column(name: "rdv_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "rdv_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rdv_style", type: "VARCHAR(255)")

            column(name: "rdv_value", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rdv_owner", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "rdv_label", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-18") {
        createTable(tableName: "remotekb") {
            column(name: "rkb_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "rkb_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rkb_creds", type: "VARCHAR(255)")

            column(name: "rkb_cursor", type: "VARCHAR(255)")

            column(name: "rkb_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rkb_active", type: "BOOLEAN")

            column(name: "rkb_type", type: "VARCHAR(255)")

            column(name: "rkb_principal", type: "VARCHAR(255)")

            column(name: "rkb_list_prefix", type: "VARCHAR(255)")

            column(name: "rkb_full_prefix", type: "VARCHAR(255)")

            column(name: "rkb_uri", type: "VARCHAR(255)")

            column(name: "rkb_supports_harvesting", type: "BOOLEAN")

            column(name: "rkb_rectype", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-19") {
        createTable(tableName: "sa_event_history") {
            column(name: "eh_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "eh_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "eh_event_date", type: "TIMESTAMP WITHOUT TIME ZONE") {
                constraints(nullable: "false")
            }

            column(name: "eh_summary", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "eh_event_data", type: "VARCHAR(255)")

            column(name: "eh_owner", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "eh_notes", type: "VARCHAR(255)")

            column(name: "eh_event_outcome", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "eh_event_type", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-20") {
        createTable(tableName: "subscription_agreement") {
            column(name: "sa_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "sa_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "sa_cancellation_deadline", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "sa_renewal_date", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "sa_vendor_fk", type: "VARCHAR(36)")

            column(name: "sa_agreement_type", type: "VARCHAR(36)")

            column(name: "sa_renewal_priority", type: "VARCHAR(36)")

            column(name: "sa_content_review_needed", type: "VARCHAR(36)")

            column(name: "sa_end_date", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "sa_vendor_reference", type: "VARCHAR(255)")

            column(name: "sa_is_perpetual", type: "VARCHAR(36)")

            column(name: "sa_start_date", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "sa_next_review_date", type: "TIMESTAMP WITHOUT TIME ZONE")

            column(name: "sa_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "sa_local_reference", type: "VARCHAR(255)")

            column(name: "sa_agreement_status", type: "VARCHAR(36)")

            column(name: "sa_enabled", type: "BOOLEAN")

            column(name: "sa_description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-21") {
        createTable(tableName: "tag") {
            column(name: "tag_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "tag_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "tag_owner_domain", type: "VARCHAR(255)")

            column(name: "tag_owner_id", type: "VARCHAR(255)")

            column(name: "tag_value", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-22") {
        createTable(tableName: "title_instance") {
            column(name: "ti_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "ti_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ti_title", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ti_work_fk", type: "VARCHAR(36)")

            column(name: "ti_medium_fk", type: "VARCHAR(36)")

            column(name: "ti_resource_type_fk", type: "VARCHAR(36)")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-23") {
        createTable(tableName: "work") {
            column(name: "w_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "w_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "w_title", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-24") {
        addPrimaryKey(columnNames: "id", constraintName: "all_electronic_resourcesPK", tableName: "all_electronic_resources")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-25") {
        addPrimaryKey(columnNames: "cs_id", constraintName: "coverage_statementPK", tableName: "coverage_statement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-26") {
        addPrimaryKey(columnNames: "ent_id", constraintName: "entitlementPK", tableName: "entitlement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-27") {
        addPrimaryKey(columnNames: "co_id", constraintName: "holdings_coveragePK", tableName: "holdings_coverage")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-28") {
        addPrimaryKey(columnNames: "id_id", constraintName: "identifierPK", tableName: "identifier")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-29") {
        addPrimaryKey(columnNames: "idns_id", constraintName: "identifier_namespacePK", tableName: "identifier_namespace")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-30") {
        addPrimaryKey(columnNames: "io_id", constraintName: "identifier_occurrencePK", tableName: "identifier_occurrence")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-31") {
        addPrimaryKey(columnNames: "ic_id", constraintName: "internal_contactPK", tableName: "internal_contact")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-32") {
        addPrimaryKey(columnNames: "nd_id", constraintName: "nodePK", tableName: "node")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-33") {
        addPrimaryKey(columnNames: "org_id", constraintName: "orgPK", tableName: "org")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-34") {
        addPrimaryKey(columnNames: "pkg_id", constraintName: "packagePK", tableName: "package")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-35") {
        addPrimaryKey(columnNames: "pci_id", constraintName: "package_content_itemPK", tableName: "package_content_item")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-36") {
        addPrimaryKey(columnNames: "pt_id", constraintName: "platformPK", tableName: "platform")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-37") {
        addPrimaryKey(columnNames: "pl_id", constraintName: "platform_locatorPK", tableName: "platform_locator")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-38") {
        addPrimaryKey(columnNames: "pti_id", constraintName: "platform_title_instancePK", tableName: "platform_title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-39") {
        addPrimaryKey(columnNames: "rdc_id", constraintName: "refdata_categoryPK", tableName: "refdata_category")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-40") {
        addPrimaryKey(columnNames: "rdv_id", constraintName: "refdata_valuePK", tableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-41") {
        addPrimaryKey(columnNames: "rkb_id", constraintName: "remotekbPK", tableName: "remotekb")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-42") {
        addPrimaryKey(columnNames: "eh_id", constraintName: "sa_event_historyPK", tableName: "sa_event_history")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-43") {
        addPrimaryKey(columnNames: "sa_id", constraintName: "subscription_agreementPK", tableName: "subscription_agreement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-44") {
        addPrimaryKey(columnNames: "tag_id", constraintName: "tagPK", tableName: "tag")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-45") {
        addPrimaryKey(columnNames: "ti_id", constraintName: "title_instancePK", tableName: "title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-46") {
        addPrimaryKey(columnNames: "w_id", constraintName: "workPK", tableName: "work")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-47") {
        createIndex(indexName: "rdv_entry_idx", tableName: "refdata_value") {
            column(name: "rdv_value")

            column(name: "rdv_owner")
        }
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-48") {
        addForeignKeyConstraint(baseColumnNames: "io_identifier_fk", baseTableName: "identifier_occurrence", constraintName: "FK124sp9vc5hnix1ufo6wi2vbav", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id_id", referencedTableName: "identifier")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-49") {
        addForeignKeyConstraint(baseColumnNames: "ent_pti_fk", baseTableName: "entitlement", constraintName: "FK17wfbfxkt6rnvi3ocbckkifsw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pti_id", referencedTableName: "platform_title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-50") {
        addForeignKeyConstraint(baseColumnNames: "cs_ti_fk", baseTableName: "coverage_statement", constraintName: "FK2ocimr1uh2pogta68xl9ph3n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ti_id", referencedTableName: "title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-51") {
        addForeignKeyConstraint(baseColumnNames: "nd_parent", baseTableName: "node", constraintName: "FK2x99i2kqqt7g2ik5cn2fmif6t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "nd_id", referencedTableName: "node")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-52") {
        addForeignKeyConstraint(baseColumnNames: "sa_renewal_priority", baseTableName: "subscription_agreement", constraintName: "FK34wtnrq42y7hiab2pg918y7en", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-53") {
        addForeignKeyConstraint(baseColumnNames: "sa_content_review_needed", baseTableName: "subscription_agreement", constraintName: "FK4nhteulih6q3nqtsu512ny93x", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-54") {
        addForeignKeyConstraint(baseColumnNames: "pci_pkg_fk", baseTableName: "package_content_item", constraintName: "FK4u9t780a3pgjy1wxsdn8r131k", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pkg_id", referencedTableName: "package")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-55") {
        addForeignKeyConstraint(baseColumnNames: "sa_agreement_type", baseTableName: "subscription_agreement", constraintName: "FK613exmd4qa6bjjdycx9kot0yp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-56") {
        addForeignKeyConstraint(baseColumnNames: "pti_id", baseTableName: "all_electronic_resources", constraintName: "FK6d3yivwmfpfvfmg7wlgg4ldia", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pti_id", referencedTableName: "platform_title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-57") {
        addForeignKeyConstraint(baseColumnNames: "ti_work_fk", baseTableName: "title_instance", constraintName: "FK6jfb5y930akyqphqjt55yrga6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "w_id", referencedTableName: "work")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-58") {
        addForeignKeyConstraint(baseColumnNames: "ic_owner_fk", baseTableName: "internal_contact", constraintName: "FK7p34rfl5q8gij3717gpkxq4yt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "sa_id", referencedTableName: "subscription_agreement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-59") {
        addForeignKeyConstraint(baseColumnNames: "co_ent_fk", baseTableName: "holdings_coverage", constraintName: "FK7tx1qaa6hcl1p5kg4n9k8fv4d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ent_id", referencedTableName: "entitlement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-60") {
        addForeignKeyConstraint(baseColumnNames: "sa_is_perpetual", baseTableName: "subscription_agreement", constraintName: "FK8g7c4fgbop5kuy91di5eh9luq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-61") {
        addForeignKeyConstraint(baseColumnNames: "io_status_fk", baseTableName: "identifier_occurrence", constraintName: "FK930t3v9wtioa9a9j5013au5ci", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-62") {
        addForeignKeyConstraint(baseColumnNames: "eh_event_outcome", baseTableName: "sa_event_history", constraintName: "FK9hxymcjll2kctbwvm60j8ywxv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-63") {
        addForeignKeyConstraint(baseColumnNames: "ent_pkg_fk", baseTableName: "entitlement", constraintName: "FKa34sbc1g2yr1gwdxxp8yb5yo3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pkg_id", referencedTableName: "package")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-64") {
        addForeignKeyConstraint(baseColumnNames: "io_ti_fk", baseTableName: "identifier_occurrence", constraintName: "FKat7yej3qg0w5ppb0t4akj51wl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ti_id", referencedTableName: "title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-65") {
        addForeignKeyConstraint(baseColumnNames: "id_ns_fk", baseTableName: "identifier", constraintName: "FKby5jjtajics8edtt193lwtnwv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "idns_id", referencedTableName: "identifier_namespace")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-66") {
        addForeignKeyConstraint(baseColumnNames: "cs_pci_fk", baseTableName: "coverage_statement", constraintName: "FKciqq54dwgdmv0ta5ugs58sn36", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pci_id", referencedTableName: "package_content_item")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-67") {
        addForeignKeyConstraint(baseColumnNames: "ti_resource_type_fk", baseTableName: "title_instance", constraintName: "FKcmjwkw0qycf0qkm8jn7kbnf45", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-68") {
        addForeignKeyConstraint(baseColumnNames: "plat_fk", baseTableName: "all_electronic_resources", constraintName: "FKd265sofvabm8rixvq9pdcnqib", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pt_id", referencedTableName: "platform")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-69") {
        addForeignKeyConstraint(baseColumnNames: "cs_pti_fk", baseTableName: "coverage_statement", constraintName: "FKdj82640bdcj4dfrbn0aqdgbfp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pti_id", referencedTableName: "platform_title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-70") {
        addForeignKeyConstraint(baseColumnNames: "pti_ti_fk", baseTableName: "platform_title_instance", constraintName: "FKedoadk035beg5u3vi2232pq9m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ti_id", referencedTableName: "title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-71") {
        addForeignKeyConstraint(baseColumnNames: "eh_owner", baseTableName: "sa_event_history", constraintName: "FKeopwa26u1tipqav4a0y01i9sr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "sa_id", referencedTableName: "subscription_agreement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-72") {
        addForeignKeyConstraint(baseColumnNames: "pl_owner_fk", baseTableName: "platform_locator", constraintName: "FKfn4ls5f77sc18cq9c8owlkgtp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pt_id", referencedTableName: "platform")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-73") {
        addForeignKeyConstraint(baseColumnNames: "ti_medium_fk", baseTableName: "title_instance", constraintName: "FKgtw7ahwcc2q7b79k8qvxsca12", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-74") {
        addForeignKeyConstraint(baseColumnNames: "rdv_owner", baseTableName: "refdata_value", constraintName: "FKh4fon2a7k4y8b2sicjm0i6oy8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdc_id", referencedTableName: "refdata_category")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-75") {
        addForeignKeyConstraint(baseColumnNames: "sa_agreement_status", baseTableName: "subscription_agreement", constraintName: "FKiivriw3306iouwpg8e65t3ff0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-76") {
        addForeignKeyConstraint(baseColumnNames: "pkg_id", baseTableName: "all_electronic_resources", constraintName: "FKkm5a7brgy3ysp9iy8nobh4eg8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pkg_id", referencedTableName: "package")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-77") {
        addForeignKeyConstraint(baseColumnNames: "pkg_remote_kb", baseTableName: "package", constraintName: "FKoedx99aeb9ll9v1p7w29htqtl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rkb_id", referencedTableName: "remotekb")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-78") {
        addForeignKeyConstraint(baseColumnNames: "pkg_vendor_fk", baseTableName: "package", constraintName: "FKokps4xbl6ipd7unkfq910jn03", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "org_id", referencedTableName: "org")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-79") {
        addForeignKeyConstraint(baseColumnNames: "ent_owner_fk", baseTableName: "entitlement", constraintName: "FKoocrauwiw6xp7ace0yueywgqy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "sa_id", referencedTableName: "subscription_agreement")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-80") {
        addForeignKeyConstraint(baseColumnNames: "pci_pti_fk", baseTableName: "package_content_item", constraintName: "FKostrwqec52cid7enxbr4b2loe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pti_id", referencedTableName: "platform_title_instance")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-81") {
        addForeignKeyConstraint(baseColumnNames: "sa_vendor_fk", baseTableName: "subscription_agreement", constraintName: "FKppeugnj4xts3ah8tjmeg232db", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "org_id", referencedTableName: "org")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-82") {
        addForeignKeyConstraint(baseColumnNames: "eh_event_type", baseTableName: "sa_event_history", constraintName: "FKs8nxucxkesrpshhxsh15wxdn0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "rdv_id", referencedTableName: "refdata_value")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-83") {
        addForeignKeyConstraint(baseColumnNames: "ent_pci_fk", baseTableName: "entitlement", constraintName: "FKswrcj57l9yvw4u7u8ug9ibytl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pci_id", referencedTableName: "package_content_item")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-84") {
        addForeignKeyConstraint(baseColumnNames: "pkg_nominal_platform_fk", baseTableName: "package", constraintName: "FKtji5rpd3emxprdidedl006f9u", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pt_id", referencedTableName: "platform")
    }

    changeSet(author: "malzer (generated)", id: "1537784660621-85") {
        addForeignKeyConstraint(baseColumnNames: "pti_pt_fk", baseTableName: "platform_title_instance", constraintName: "FKtlecp40x0sb3rd9w4qi16lcu0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "pt_id", referencedTableName: "platform")
    }
}
