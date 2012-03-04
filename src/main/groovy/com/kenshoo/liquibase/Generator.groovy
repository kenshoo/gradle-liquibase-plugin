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

import groovy.text.SimpleTemplateEngine


class Generator {
  def configurationName = 'liquid.conf'

    def generateConfiguration(project) {
  	def engine = new SimpleTemplateEngine()
  	def binding = ['dbUser', 'dbPass', 'dbHost', 'dbName','contexts', 'type'].inject([:]) { m,v ->
  	  m[v]=project.hasProperty(v)? project."$v" : '';m
	}
  	  
	def conf = new ClasspathMangler().readResourceText("templates/${configurationName}")  	
	def template = engine.createTemplate(conf).make(binding)
	new File(configurationName).write(template.toString())
    }
}
