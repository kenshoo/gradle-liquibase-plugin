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

 @Test
 public void apply(){
    def standalone = new Standalone()
    def plugin = new LiquibasePlugin()
    def project = standalone.instance()
    plugin.apply(project)
    assertThat project.convention.plugins.liqui, is(notNullValue())
    assertThat dyanmicProperties.properties.keySet(),hasItems('update','reportStatus')
    assertThat standalone.tasks.update.description, containsString('Applying all changesets with given contexts to the db')
 }
  
 @Test
 public void genConf(){
     def standalone = new Standalone()
     def plugin = new LiquibasePlugin()
     def project = standalone.instance()
     plugin.apply(project)
     project.dbName = 'play'
     assertThat dyanmicProperties.properties.dbName,is(notNullValue())
     assertThat project.hasProperty('dbName'),is(true)
     standalone.tasks.genConf.doLast.call()
 }
}
