databaseChangeLog() {
    changeSet(id: 'creating play table', author: 'ronenn', context: 'regular') {
        createTable(tableName: 'play') {
            column(name: 'id', type: 'int(11)', autoIncrement: true) {
                constraints(primaryKey: true, nullable: false)
            }
            column(name: 'date', type: 'DATETIME', defaultValue: null)
        }
    }

}

