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
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse
import static org.junit.matchers.JUnitMatchers.*


class PackagingTest {
  
  def project
  def straper
  
   @Before
   public void setup(){
      straper = new ProjectStrap()
      project = straper.createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
      straper.createChangeSets()
   }
   
  @Test
  public void packaging(){
    project.projectEvaluationBroadcaster.afterEvaluate(project, null)
    project.standaloneRepackage.execute()
    project.liquidPackage.execute()
    def zipFile = new java.util.zip.ZipFile(new File("${straper.buildDir.path}/libs/${project.liquidPackage.archiveName}"))
    def entries = zipFile.entries().collect {it.name}
    assertThat entries,hasItems('src/') 
    assertThat entries,hasItems('resources/') 
    assertThat entries,hasItems('liquibase-standalone.jar'.toString()) 
  }	   
 
  
}
