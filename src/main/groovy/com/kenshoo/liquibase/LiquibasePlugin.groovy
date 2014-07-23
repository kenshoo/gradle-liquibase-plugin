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
package com.kenshoo.liquibase

import org.gradle.api.Plugin
import org.gradle.api.logging.Logging
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class LiquibasePlugin implements Plugin<Project> {

    def Logger logger = LoggerFactory.getLogger(this.class)
    def addPackage = true

    public class LiquibasePluginConvention {
        def configurationScript = 'liquid.conf'
    }

    void apply(Project project) {
        addLiquidTasks(project)
        addGeneratorTasks(project)
        if(addPackage){
          new Packaging().addPackagingTasks(project)
	  }
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
                def configuration = new Configuration(liqui.configurationScript)
                configuration.applyDefaults(project,taskMeta)
                def dbs =  configuration.dbs
                dbs.each {config -> 
                  new HostsAssertion().assertHostName(config.host)
                  logger.info(Logging.LIFECYCLE, "Executing ${taskMeta.name} on database ${config['name']} under hostname ${config['host']}:" )
                  invoker.invoke(project, taskMeta, strap.build(config, project))
		    }
            }
            project."${taskMeta.name}".group = 'liquibase'
        }   	
    }

    def addGeneratorTasks(project) {
       def desc = """generate liquid configuration file (liqui.conf)
Can be invoked by providing either of the following options:
genConf -PdbUser=[String value] -PdbPass=[String value] -PdbHost=[String value] -PdbName=[String value] 
       """
       project.task([description: desc], 'genConf') << {
         new Generator().generateConfiguration(project) 
       }
       project.genConf.group = 'liquibase'
    }

}
