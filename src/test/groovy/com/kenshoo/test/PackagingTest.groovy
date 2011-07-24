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
  public void wrapper(){
    def wrapperFiles = ['gradlew','gradle'].collect {"${straper.buildDir.path}/${it}"}
    project.wrapper.execute() 
    wrapperFiles.each {assertTrue(new File(it).exists())}
    project.cleanWrapper.execute() 
    wrapperFiles.each {assertFalse(new File(it).exists())}
  }

  @Test
  public void packging(){
    project.wrapper.execute() 
    project.liquidPackage.execute()
    def zipFile = new java.util.zip.ZipFile(new File("${straper.buildDir.path}/build/liquid-distributable.zip"))
    assertThat new File("${project.projectDir}/build.gradle.packaged").text, containsString("classpath 'com.kenshoo.gradle.plugins:liquibase:1.2'")
    assertThat zipFile.entries().collect {it.name},hasItems('gradle/','gradlew','gradlew.bat','src/','build.gradle') 
    project.cleanWrapper.execute()
  }	   
 
  
}
