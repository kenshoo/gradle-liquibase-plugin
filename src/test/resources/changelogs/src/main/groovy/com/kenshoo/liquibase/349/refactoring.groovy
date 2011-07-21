databaseChangeLog() {

  changeSet(id: 'create-table', author: 'ronenn') {
    createTable( tableName: 'play', remarks: 'do we care?') {
      column(name:'id',type:'int')

    }
  }
}
