package com.kenshoo.liquibase

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.parser.ChangeLogParserFactory
import liquibase.parser.ext.GroovyLiquibaseChangeLogParser
import liquibase.resource.FileSystemResourceAccessor

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 3:25 PM
 */
class LiquidStrap {


    def Liquibase build(configuration) {
        configuration.with {
            ChangeLogParserFactory.getInstance().register(new GroovyLiquibaseChangeLogParser())
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(createDs(user,pass,host,name).getConnection()))
            new Liquibase(changeLog, new FileSystemResourceAccessor(), database)
        }
    }

    def createDs(user, pass, host, name) {
        new MysqlDataSource(user: user, password: pass, serverName: host, databaseName: name)
    }

    def readProperties(configurationScript) {
        new GroovyShell().evaluate(new File(configurationScript))
    }
}
