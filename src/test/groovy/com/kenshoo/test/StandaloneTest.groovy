package com.kenshoo.test

import org.junit.Test
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.CoreMatchers.is
import static org.junit.matchers.JUnitMatchers.hasItems
import static org.junit.matchers.JUnitMatchers.containsString
import static org.junit.Assert.assertThat
import com.kenshoo.liquibase.LiquibasePlugin
import com.kenshoo.gradle.Standalone
import org.gradle.api.Project


class StandaloneTest {
 
 @Test
 public void apply(){
      def plugin = new LiquibasePlugin()
      def standalone= new Standalone()
      def project = standalone.instance()
      plugin.apply(project)
      assertThat project.convention.plugins.liqui, is(notNullValue())
      assertThat standalone.properties.keySet(),hasItems('update','reportStatus')
      assertThat standalone.tasks.update.description, containsString('Applying all changesets with given contexts to the db')
      
 }
  
}
