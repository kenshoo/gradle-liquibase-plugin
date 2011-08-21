package com.kenshoo.test

import org.gradle.testfixtures.ProjectBuilder


/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 4:21 PM
 */
class ProjectStrap {
    def buildDir = new File("${System.getProperty('user.dir')}/build/mocked-prj")
    def pluginVersion = '1.0.8'

    def createProjectWithPlugin(pluginClass) {
        buildDir.mkdir()
        def project = ProjectBuilder.builder().withProjectDir(buildDir).build()
        project.version = '1.0'
        addLiquidBuildDep(project)
        project.apply(plugin: pluginClass)
        project
    }

    def addLiquidBuildDep(project){
       project.buildscript.dependencies.add('classpath',[group:'com.kenshoo.gradle.plugins', name:'liquibase', version:pluginVersion])
       project.buildscript.repositories {
         mavenRepo(name: 'plugins-repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
	 }
    }

    def createChangeSets() {
       def ant = new AntBuilder()          
       ant.copy(toDir:"${buildDir.path}/src"){
         fileset(dir:'src/test/resources/changelogs/src')
	 }
	 ant.copy(file:'src/test/resources/changelogs/build.gradle',todir:buildDir.path)
    }
}
