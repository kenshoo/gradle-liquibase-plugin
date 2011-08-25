package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import groovy.text.SimpleTemplateEngine
import org.gradle.api.tasks.wrapper.Wrapper.PathBase.*

class Packging {
     private output  
     private resolve = {path -> "${output}/${path}" }

     def fromAction = {where,what,apply ->
         from(where){
          if(what){
            include what
	    }
          apply.delegate = delegate
          apply()
	   }
      }

     def addPackagingTasks(project){
       project.apply(plugin:'base')
       output = project.buildDir
       addPackage(project)
     }
     
     private def addPackage(project) {
	  def liquidPackage = project.task([description :'packages liquibase for deployment',type: Zip],'liquidPackage')   
	  liquidPackage.group = 'liquibase'
	  liquidPackage.version = project.hasProperty('build')?  "${project.version}_${project.build}" : project.version

	  project.configurations {
		standalone 
	  }

        def plugin = project.buildscript.configurations.classpath.dependencies.find {it.name.contains('liquibase')}
        project.dependencies {
	    standalone("${plugin.group}:${plugin.name}-standalone:${plugin.version}"){
	    	 transitive = false
	    }
	  }

	  fromAction.delegate = liquidPackage
	  [[where:project.projectDir,what:'src/**'],
	    [where:project.configurations.standalone]
	  ].each {from ->
		from.with{ 
		  fromAction(where,what,action?: {})
		}
	  }
	  
	  liquidPackage.with {
	     archiveName="liquid-distributable-${version}.zip"
           destinationDir = project.buildDir
	  }

	}

}
