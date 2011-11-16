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

import com.kenshoo.liquibase.LiquibasePlugin
import com.kenshoo.gradle.Standalone
import com.kenshoo.gradle.DyanmicProperties
import com.kenshoo.gradle.ArgsParser
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import org.gradle.api.Project


class ArgsParsingTest {

 def dyanmicProperties = DyanmicProperties.instance
 def project
 def standalone

  @Before
  public void setup(){
     standalone = new Standalone()
     project = standalone.instance()
  }

 
  @Test
  public void sanity(){
    new ArgsParser().apply(project, ["update","-Pcontexts=play"])
    assertThat project.contexts, is(equalTo('play'))
  }
  
  @Test
  public void twoArguments(){
    new ArgsParser().apply(project, ["reportStatus","-Pcontexts=heavy","-Pverbose=false"])
    assertThat project.contexts, is(equalTo('heavy'))
    assertThat project.verbose, is(equalTo('false'))
  }

  @Test
  public void quotedArguments(){
    new ArgsParser().apply(project, ['generateDocumentation', "-Pcontexts=\"regular\"","-PoutputDirectory=\"/tmp\""])
    assertThat project.contexts, is(equalTo('regular'))
    assertThat project.outputDirectory, is(equalTo('/tmp'))
  }


  @Test
  public void noArguments(){
    new ArgsParser().apply(project, ["reportStatus"])
  }

  @Test(expected=RuntimeException.class)
  public void nothingWrongParam(){
    new ArgsParser().apply(project, ["reportStatus","tasks"])
  }



}
