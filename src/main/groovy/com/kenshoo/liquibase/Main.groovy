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
import org.apache.log4j.BasicConfigurator

class Main {
    public static void main(String[] args) {
       BasicConfigurator.configure()
    	 def task = args.first()
    	 def standalone = new Standalone()
       def project = standalone.instance()
    	 new ArgsParser().apply(project,args) 
       new LiquibasePlugin().apply(project)
       if(!standalone.tasks."$task"){
          println("no task named ${task} found, run tasks in order to see which tasks are available.")
          System.exit(1)
	 }
       standalone.tasks."$task".doLast()
    }
}
