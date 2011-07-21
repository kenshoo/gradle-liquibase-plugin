databaseChangeLog() {

  changeSet(id: 'add-index', author: 'ronenn') {
    createIndex(tableName:"play", indexName:"idx_id"){
	  column(name:"id")
    }
  }

}
