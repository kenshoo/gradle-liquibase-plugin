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

import org.junit.Before
import org.junit.After
import org.junit.Test
import com.kenshoo.liquibase.*
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
 



class GeneratorTest {

 def project
  
 @Before
 public void setup(){
   def straper = new ProjectStrap()
   project = straper.createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
 }
   

 @After
 public void cleanup(){
  new File('liquid.conf').delete()
 }
 
 @Test
 public void confGeneration(){
    def gen = new Generator()
    gen.generateConfiguration(project)
    def expected = "[user: '', pass: '', host: '', name: '', changeLog: 'src/main/groovy/com/kenshoo/liquibase/all_versions.groovy']"
    assertThat new File(gen.configurationName).text.trim(), containsString(expected)
 }

 @Test
 public void parameterGeneration(){
    def gen = new Generator()
    [dbUser:'ronen',dbPass:'1234',dbHost:'mysql1',dbName:'play'].each {k,v -> project."$k" = v}
    gen.generateConfiguration(project)
    def expected = "[user: 'ronen', pass: '1234', host: 'mysql1', name: 'play', changeLog: 'src/main/groovy/com/kenshoo/liquibase/all_versions.groovy']"
    assertThat new File(gen.configurationName).text.trim(), containsString(expected)
 }
}
