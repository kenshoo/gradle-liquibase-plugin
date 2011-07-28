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
    assertThat project.contexts, equalTo('refactor,performance,qa')
  }

  @Test
  public void withoutDefaults() {
    project.contexts = 'yeap'
    def configuration = new Configuration('src/test/resources/liquid_without_defaults.conf')
    configuration.applyDefaults(project, tasks['update'])
    assertThat project.contexts, equalTo('yeap')
  }
 
  @Test
  public void notOverridingUser() {
    project.contexts = 'yeap'
    def configuration = new Configuration('src/test/resources/liquid.conf')
    configuration.applyDefaults(project, tasks['update'])
    assertThat project.contexts, equalTo('yeap')

  }

}
