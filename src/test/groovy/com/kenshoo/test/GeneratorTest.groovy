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
    def expected = "[user: '', pass: '', host: '', name: '', changeLog: 'src/main/groovy/com/kenshoo/liquibase/349/main.groovy']"
    assertThat new File(gen.configurationName).text.trim(), containsString(expected)
 }

 @Test
 public void parameterGeneration(){
    def gen = new Generator()
    [dbUser:'ronen',dbPass:'1234',dbHost:'mysql1',dbName:'play'].each {k,v -> project."$k" = v}
    gen.generateConfiguration(project)
    def expected = "[user: 'ronen', pass: '1234', host: 'mysql1', name: 'play', changeLog: 'src/main/groovy/com/kenshoo/liquibase/349/main.groovy']"
    assertThat new File(gen.configurationName).text.trim(), containsString(expected)
 }
}
