databaseChangeLog() {

  importCustom(addData:'liquibase.custom.ExampleCustomChange') 

  changeSet(id: 'create-table', author: 'tlberglund') {
    createTable(tableName: 'play', remarks: 'do we care?') {
      column(name:'id',type:'int')
      column(name:'name',type:'varchar(255)')
    }
  }

  changeSet(id: 'bla', author: 'ronen') {
   addData(tableName:'play'){
       columnName("id") 
       newValue(1)
   }
  }
}
