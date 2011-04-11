package com.kenshoo.liquibase

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.parser.ChangeLogParserFactory
import liquibase.parser.ext.GroovyLiquibaseChangeLogParser
import liquibase.resource.FileSystemResourceAccessor

class Main {
    public static void main(String[] args) {
        new Main().play()
    }


    def play() {
        ChangeLogParserFactory.getInstance().register(new GroovyLiquibaseChangeLogParser())
        def ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource(user: 'ronenn', password: 'ronenn', serverName: 'fiona',databaseName: 'ronenn')
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(ds.getConnection()))
        def changeLogFile = 'src/test/resources/changelogs/play-changelog.groovy'
        Liquibase liquibase = new Liquibase(changeLogFile, new FileSystemResourceAccessor(), database)
        report(liquibase)
        liquibase.update('')
        report(liquibase)
    }

    private def report(Liquibase liquibase) {
        def writer = new StringWriter()
        liquibase.reportStatus(true, "", writer)
        println writer.toString()
    }
}