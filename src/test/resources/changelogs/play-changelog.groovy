databaseChangeLog() {

  importCustom(rename:'liquibase.custom.ExampleCustomChange') 

  changeSet(id: 'create-table', author: 'tlberglund') {
    createTable(tableName: 'play', remarks: 'do we care?') {
      column(name:'id',type:'int')
      column(name:'name',type:'varchar(255)')
    }
   insert(tableName:"play"){
    column(name:"id", valueNumeric:1)
    column(name:"name", value:"haim")
   }
  }

  changeSet(id: 'bla', author: 'ronen') {
   rename(tableName:'play'){
       columnName("name") 
       newValue("'moshe'")
   }
  }
}
