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
import com.kenshoo.gradle.ArgsParser

class Main {
    public static void main(String[] args) {
    	 def task = args.first()
       def project = new Standalone().instance()
    	 new ArgsParser().apply(project,args) 
       new LiquibasePlugin().apply(project)
       project."$task".doLast()
    }
}
