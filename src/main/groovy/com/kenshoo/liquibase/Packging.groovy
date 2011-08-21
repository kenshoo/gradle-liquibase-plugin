package com.kenshoo.liquibase

import org.gradle.api.tasks.wrapper.Wrapper 
import org.gradle.api.tasks.bundling.Zip
import groovy.text.SimpleTemplateEngine
import org.gradle.api.tasks.wrapper.Wrapper.PathBase.*

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
       liquidPackage.version = project.hasProperty('build')?  "${project.version}_${project.build}" : project.version
       liquidPackage.with {
          	archiveName="liquid-distributable-${version}.zip"
            destinationDir=output
            from(output){
              fileMode = 0775
              include 'gradlew'
		}

            from(project.projectDir){
              include 'src/**'
		}

            from(output){
              include 'gradle/**','gradlew.bat','wrapper/dists/*.zip'
		}

            from(output){
              include 'build.gradle.packaged'
              rename {
                'build.gradle'
		  }
		}

            from project.buildscript.configurations.classpath 
	 }

	 liquidPackage.doFirst {
	    generateBuildGradle(project)
	    prefetchWrapper(project)
	 }
     }

     private def addWrapper(project) {
       project.task([type:Wrapper],'wrapper').configure {
         gradleVersion = '0.9.2'
         disterbutionUrl = 'http://bob:8081/artifactory/repo'
         scriptDestinationPath = output
         jarPath = resolve("gradle/wrapper")
         archiveBase = Wrapper.PathBase.PROJECT
         distributionBase = Wrapper.PathBase.PROJECT
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

     def prefetchWrapper(project){
     	 def process  = "./gradlew tasks".execute(null, project.buildDir)
     	 process.waitFor()
     	 assert process.exitValue() == 0
     }
}
