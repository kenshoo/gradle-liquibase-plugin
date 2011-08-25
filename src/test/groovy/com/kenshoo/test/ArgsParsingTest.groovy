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
  public void noArguments(){
    new ArgsParser().apply(project, ["reportStatus"])
  }

  @Test(expected=RuntimeException.class)
  public void nothingWrongParam(){
    new ArgsParser().apply(project, ["reportStatus","tasks"])
  }

}
