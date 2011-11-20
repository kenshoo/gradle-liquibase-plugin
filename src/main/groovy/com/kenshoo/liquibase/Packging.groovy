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
	    standalone("${plugin.group}:liquibase-standalone:${plugin.version}"){
	    	 transitive = false
	    }
	  }

	  fromAction.delegate = liquidPackage
	  [
	    [where:project.projectDir,what:'src/**'],
	    [where:project.projectDir,what:'resources/**'],
	    [where:project.configurations.standalone,action:{rename{"liquibase-standalone.jar"}}]
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
