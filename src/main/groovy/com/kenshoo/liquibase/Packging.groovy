package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import groovy.text.SimpleTemplateEngine

class Packging {


     private output  
     private resolve = {path -> "${output}/${path}" }
     
     def addPackagingTasks(project){
       project.apply(plugin:'base')
       output = project.buildDir
       addWrapper(project)
       addPackage(project)
     }
     
     private def addPackage(project) {
       def liquidPackage = project.task([description :'packages liquibase for deployment',type: Zip],'liquidPackage')   
       liquidPackage.group = 'liquibase'

       liquidPackage.with{
          	archiveName='liquid-distributable.zip'
            destinationDir=output
            from(output){
              fileMode = 0775
              include 'gradlew'
		}

            from(project.projectDir){
              include 'src/**'
		}

            from(output){
              include 'gradle/**','gradlew.bat'
		}

            from(output){
              include 'build.gradle.packaged'
              rename {
                'build.gradle'
		  }
		}
	 }

	 liquidPackage.doFirst {
	    generateBuildGradle(project)
	 }

     }

     private def addWrapper(project) {
       project.task([type:Wrapper],'wrapper').configure {
        gradleVersion = '0.9.2'
        disterbutionUrl = 'http://bob:8081/artifactory/repo'
        scriptDestinationPath = output
        jarPath = resolve("gradle/wrapper")
	 }

       project.wrapper.outputs.files resolve('gradlew.bat')
       ['gradle','userHome'].each{ project.wrapper.outputs.dir resolve(it) }
     }

     def generateBuildGradle(project) {
        project.liquidPackage.outputs.files 'build.gradle.packaged'
        def version = project.buildscript.configurations.getByName('classpath').dependencies.find {it.name.equals('liquibase')}.version
        def engine = new SimpleTemplateEngine()
        def binding = [version:version]
        def text = new ClasspathMangler().readResourceText('templates/build.gradle.gsp')
        def template = engine.createTemplate(text).make(binding)
        new File(resolve('build.gradle.packaged')).write(template.toString())
     }
  
}
