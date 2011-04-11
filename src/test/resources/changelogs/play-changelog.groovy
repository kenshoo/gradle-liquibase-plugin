databaseChangeLog() {

  changeSet(id: 'create-table', author: 'tlberglund') {
    createTable( tableName: 'play', remarks: 'do we care?') {
      column(name:'id',type:'int')

    }
  }
}
