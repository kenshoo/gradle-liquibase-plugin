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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Main {

    public static void main(String[] args) {

      def Logger logger = LoggerFactory.getLogger(this.class);

    	try {
	    // BasicConfigurator.configure()
	    def standalone = new Standalone()
	    def project = standalone.instance()
	    new ArgsParser().apply(project,args) 
	    new LiquibasePlugin(addPackage:false).apply(project)

	    if(!args){
		  throw new RuntimeException("Please select which task to run (run tasks in order to see available tasks).")
	    }

	    def task = args.first()

	    if(!standalone.tasks."$task"){
		  throw new RuntimeException("no task named ${task} found, run tasks in order to see which tasks are available.")
	    }

	    standalone.tasks."$task".doLast()
	} catch(e){
	  logger.error(e.message)
        System.exit(1)
	}
    }
}
