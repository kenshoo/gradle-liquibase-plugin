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

import org.junit.Test
import org.junit.Before
import org.junit.After
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.CoreMatchers.is
import static org.junit.matchers.JUnitMatchers.hasItems
import static org.junit.matchers.JUnitMatchers.containsString
import static org.junit.Assert.assertThat
import com.kenshoo.liquibase.LiquibasePlugin
import com.kenshoo.gradle.Standalone
import com.kenshoo.gradle.DyanmicProperties
import org.gradle.api.Project


class StandaloneTest {

 def dyanmicProperties = DyanmicProperties.instance
 def project
 def standalone

 @Before
 public void setup(){
    standalone = new Standalone()
    project = standalone.instance()
    def plugin = new LiquibasePlugin(addPackage:false)
    plugin.apply(project)
 }

 @Test
 public void apply(){
    assertThat project.convention.plugins.liqui, is(notNullValue())
    assertThat dyanmicProperties.properties.keySet(),hasItems('update','reportStatus')
    assertThat standalone.tasks.update.description, containsString('Applying all changesets with given contexts to the db')
 }
  
 @Test
 public void genConf(){
     project.dbName = 'play'
     assertThat dyanmicProperties.properties.dbName,is(notNullValue())
     assertThat project.hasProperty('dbName'),is(true)
     standalone.tasks.genConf.doLast()
     assertThat new File('liquid.conf').text, containsString("name: 'play'")
 }

 @Test
 public void update(){
     def liqui = project.convention.plugins.liqui
     liqui.configurationScript = 'src/test/resources/liquid.conf'
     project.contexts = ''
     standalone.tasks.update.doLast()
 }
}
