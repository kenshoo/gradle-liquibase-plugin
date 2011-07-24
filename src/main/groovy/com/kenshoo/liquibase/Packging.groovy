package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import groovy.text.SimpleTemplateEngine

class Packging {
     def addPackagingTasks(project){
       addWrapper(project)
       addPackage(project)
     }
     
     private def addPackage(project) {
       def liquidPackage = project.task([description :'packages liquibase for deployment',type: Zip],'liquidPackage')   
       liquidPackage.group = 'liquibase'

       liquidPackage.with{
          	archiveName='liquid-distributable.zip'
            destinationDir=project.buildDir
            from(project.projectDir){
              fileMode = 0775
              include 'gradlew'
		}
            from(project.projectDir){
              include 'gradle/**','gradlew.bat','src/**'
		}

            from(project.projectDir){
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
	 }

       project.apply(plugin:'base')
       project.wrapper.outputs.files 'gradlew.bat'
       ['gradle','userHome'].each{ project.wrapper.outputs.dir it }
     }

     def generateBuildGradle(project) {
        project.liquidPackage.outputs.files 'build.gradle.packaged'
        def version = project.buildscript.configurations.getByName('classpath').dependencies.find {it.name.equals('liquibase')}.version
        def engine = new SimpleTemplateEngine()
        def binding = [version:version]
        def text = new ClasspathMangler().readResourceText('templates/build.gradle.gsp')
        def template = engine.createTemplate(text).make(binding)
        template.toString()
        new File("${project.projectDir}/build.gradle.packaged").write(template.toString())
     }
  
}
