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
package com.kenshoo.test

import org.gradle.testfixtures.ProjectBuilder


/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 4:21 PM
 */
class ProjectStrap {
    def buildDir = new File("${System.getProperty('user.dir')}/build/")
    def pluginVersion = '1.0.10'

    def createProjectWithPlugin(pluginClass) {
        buildDir.mkdir()
        def project = ProjectBuilder.builder().withProjectDir(buildDir).build()
        project.buildDir = buildDir
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
       project.repositories {
         mavenRepo(name: 'plugins-repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
	 }
    }

    def createChangeSets() {
       def ant = new AntBuilder()          
       new File("${buildDir.path}/resources").mkdir()
       ant.copy(toDir:"${buildDir.path}/src"){
         fileset(dir:'src/test/resources/changelogs/src')
	 }
	 ant.copy(file:'src/test/resources/changelogs/build.gradle',todir:buildDir.path)
    }
}
