package com.kenshoo.liquibase

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.parser.ChangeLogParserFactory
import liquibase.parser.ext.GroovyLiquibaseChangeLogParser
import liquibase.resource.FileSystemResourceAccessor
import org.gradle.api.Project
import com.kenshoo.gradle.Standalone

class Main {
    public static void main(String[] args) {
       new LiquibasePlugin().apply(new Standalone().instance())
    }
}
