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

    def createProjectWithPlugin(pluginClass) {
        buildDir.mkdir()
        def project = ProjectBuilder.builder().withProjectDir(buildDir).build()
        project.apply(plugin: pluginClass)
        project
    }

    def createChangeSets() {
       def ant = new AntBuilder()          
       ant.copy(toDir:"${buildDir.path}/src"){
         fileset(dir:'src/test/resources/changelogs/src')
	 }
	 ant.copy(file:'src/test/resources/changelogs/build.gradle',todir:buildDir.path)
    }
}
