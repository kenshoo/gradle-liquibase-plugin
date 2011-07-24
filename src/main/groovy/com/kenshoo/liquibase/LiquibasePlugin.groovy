package com.kenshoo.liquibase

import org.gradle.api.Plugin
import org.gradle.api.logging.Logging
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory


 /**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 3:00 PM
 */
class LiquibasePlugin implements Plugin<Project> {

    def Logger logger = LoggerFactory.getLogger(this.class)

    public class LiquibasePluginConvention {
        def configurationScript = 'liquid.conf'
    }


    void apply(Project project) {
        addLiquidTasks(project)
        addGeneratorTasks(project)
        new Packging().addPackagingTasks(project)
    }

    def addLiquidTasks(project) {
        project.convention.plugins.liqui = new LiquibasePluginConvention()
        def resolver = new LiquibaseApiResolver()
        def methods = resolver.readAllApiMethods()
        resolver.convertMethodToTasks(methods).each {name, taskMeta ->
            project.task([description: taskMeta.desc()], taskMeta.name) << {
                def invoker = new LiquiMethodInvoker()
                def liqui = project.convention.plugins.liqui
                def strap = new LiquidStrap()
                def props =  strap.readProperties(liqui.configurationScript)
                props.each {config -> 
                  new HostsAssertion().assertHostName(config.host)
                  logger.info(Logging.LIFECYCLE, "Executing ${taskMeta.name} on database ${config['name']} under hostname ${config['host']}:" )
                  invoker.invoke(project, taskMeta, strap.build(config))
		    }
            }
            project."${taskMeta.name}".group = 'liquibase'
        }   	
    }

    def addGeneratorTasks(project) {
       project.task([description: "generate liquid configuration file (liqui.conf)\n"], "genConf") << {
         new Generator().generateConfiguration() 
       }
       project.genConf.group = 'liquibase'
    }

}
