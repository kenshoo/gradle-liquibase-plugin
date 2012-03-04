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

class Configuration {

  def dbs
    def defaults

    def Configuration(configurationScript) {
	def conf = new GroovyShell().evaluate(new File(configurationScript))
	dbs = conf.dbs
	defaults = conf.defaults
    }

  def applyDefaults(project,taskMeta){
    def params = taskMeta.paramsWithoutNonProvided().inject([] as Set){set,list->
	list.each {param -> set << param.name }
	set
    }

    if(defaults){
	defaults.keySet().intersect(params).each{d -> 
	  if(!project.hasProperty(d)){
	    project."$d"=defaults."$d" 
	  }
	}
    }
  }


}
