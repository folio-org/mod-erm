databaseChangeLog = {
  changeSet(author: "ethanfreestone (manual)", id: "202001141001-001") {
    addColumn(tableName: "title_instance") {
      column(name: "ti_date_monograph_published_print", type: "timestamp")
      column(name: "ti_date_monograph_published_online", type: "timestamp")
      column(name: "ti_first_author", type: "VARCHAR(36)")
      column(name: "ti_monograph_edition", type: "VARCHAR(36)")
      column(name: "ti_monograph_volume", type: "VARCHAR(36)")
    }
  }
}
