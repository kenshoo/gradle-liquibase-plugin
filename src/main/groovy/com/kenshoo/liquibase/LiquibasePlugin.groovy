package com.kenshoo.liquibase

import org.gradle.api.Plugin
import org.gradle.api.Project

 /**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 3:00 PM
 */
class LiquibasePlugin implements Plugin<Project> {

    public class LiquibasePluginConvention {
        def configurationScript = 'liquid.conf'
    }

    void apply(Project project) {
        project.convention.plugins.liqui = new LiquibasePluginConvention()
        def resolver = new LiquibaseApiResolver()
        def methods = resolver.readAllApiMethods()
        resolver.convertMethodToTasks(methods).each {name, taskMeta ->
            project.task([description: taskMeta.desc()], taskMeta.name) << {
                def invoker = new LiquiMethodInvoker()
                def liqui = project.convention.plugins.liqui
                invoker.invoke(project, taskMeta, new LiquidStrap().build(liqui.configurationScript))
            }
            project."${taskMeta.name}".group = 'liquibase'
        }
    }



}
