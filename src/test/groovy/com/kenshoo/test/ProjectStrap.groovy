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
    }
}
