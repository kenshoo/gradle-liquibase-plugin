 /*
* Copyright 2011 Kenshoo.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/  
package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import org.gradle.api.tasks.bundling.Jar
import groovy.text.SimpleTemplateEngine
import org.gradle.api.tasks.wrapper.Wrapper.PathBase.*

class Packaging {

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
       addPackage(project)
       addStandaloneRepackage(project)
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
	    standalone("${plugin.group}:liquibase-standalone:${plugin.version}"){
	    	 transitive = false
	    }
	  }


	  fromAction.delegate = liquidPackage
	  [
	    [where:project.projectDir,what:'src/**'],
	    [where:project.projectDir,what:'resources/**'],
	    [where:project.libsDir,what:'liquibase-standalone.jar']
	  ].each {from ->
		from.with{ 
		  fromAction(where,what,action?: {})
		}
	  }
	  
	  liquidPackage.with {
	     archiveName="liquid-distributable_${version}.zip"
           destinationDir = project.libsDir
	  }

        liquidPackage.outputs.files liquidPackage.archivePath
        liquidPackage.outputs.dir liquidPackage.destinationDir
     }

     private def addStandaloneRepackage(project){
       project.apply(plugin:'java')
       project.buildscript.configurations.add('custom')
       project.afterEvaluate {
       project.task([description :'Re-packages liquibase standalone before its added to the zip file'],'standaloneRepackage') << {
         project.ant.jar (jarfile:"${project.libsDir}/liquibase-standalone.jar"){
          project.configurations.standalone.files.each {file -> 
            zipfileset(src:file.path)
	    }
          project.buildscript.configurations.custom.files.each {file -> 
            zipfileset(src:file.path)
	    }
          manifest {
            attribute(name:'Main-Class', value:'com.kenshoo.liquibase.Main')
          }
	   }
        }
        project.liquidPackage.dependsOn(project.standaloneRepackage)
        project.standaloneRepackage.group = 'liquibase'
	 }
     }

}
