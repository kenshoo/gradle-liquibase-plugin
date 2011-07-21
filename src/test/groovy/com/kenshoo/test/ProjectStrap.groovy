package com.kenshoo.test

import org.gradle.testfixtures.ProjectBuilder

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 4:21 PM
 */
class ProjectStrap {
    def createProjectWithPlugin(pluginClass) {
        def project = ProjectBuilder.builder().withProjectDir(new File(System.getProperty('user.dir'))).build()
        project.apply(plugin: pluginClass)
        project
    }
}
