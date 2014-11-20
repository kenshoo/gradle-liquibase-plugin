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

import org.junit.Test
import com.kenshoo.liquibase.Configuration
import com.kenshoo.test.ProjectStrap
import org.junit.Before
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import org.hamcrest.core.*

class ConfigurationTest {
  
  def project
  def tasks
  @Before
  public void setup(){
    project = new ProjectStrap().createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
    def resolver = new LiquibaseApiResolver()
    def methods = resolver.readAllApiMethods()
    tasks = resolver.convertMethodToTasks(methods)
  }

  @Test
  public void defaults(){
    def configuration = new Configuration('src/test/resources/liquid.conf')
    configuration.applyDefaults(project, tasks['update'])
    assertThat project.ext.contexts, equalTo('refactor,performance,qa')
  }

  @Test
  public void withoutDefaults() {
    project.ext.contexts = 'yeap'
    def configuration = new Configuration('src/test/resources/liquid_without_defaults.conf')
    configuration.applyDefaults(project, tasks['update'])
    assertThat project.ext.contexts, equalTo('yeap')
  }
 
  @Test
  public void notOverridingUser() {
    project.ext.contexts = 'yeap'
    def configuration = new Configuration('src/test/resources/liquid.conf')
    configuration.applyDefaults(project, tasks['update'])
    assertThat project.ext.contexts, equalTo('yeap')

  }

}
