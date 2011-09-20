package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import groovy.text.SimpleTemplateEngine
import org.gradle.api.tasks.wrapper.Wrapper.PathBase.*

class Packging {

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
       addPackage(project)
     }
     
     private def addPackage(project) {
     	  project.apply(plugin:'base')
	  def liquidPackage = project.task([description :'packages liquibase for deployment',type: Zip],'liquidPackage')   
	  liquidPackage.group = 'liquibase'
	  liquidPackage.version = project.hasProperty('buildNum')?  "${project.version}_${project.buildNum}" : project.version

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
	    [where:project.configurations.standalone,action:{rename{"${plugin.name}-standalone.jar"}}]
	  ].each {from ->
		from.with{ 
		  fromAction(where,what,action?: {})
		}
	  }
	  
	  liquidPackage.with {
	     archiveName="liquid-distributable_${version}.zip"
           destinationDir = new File("${project.buildDir}/libs/")
	  }

        liquidPackage.outputs.files liquidPackage.archivePath
        liquidPackage.outputs.dir liquidPackage.destinationDir
      }

}
