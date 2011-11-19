databaseChangeLog() {
    changeSet(id: 'Adding foo column',  author: 'ronenn', context: 'regular') {
	addColumn(tableName: 'play') {
	  column(name: 'random', type: 'TINYINT(4)') {
	    constraints(nullable: false)
	  }
	}
    }
}

