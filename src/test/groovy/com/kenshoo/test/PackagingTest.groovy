package com.kenshoo.test

import org.junit.Test
import org.junit.Before
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse


class PackagingTest {
  
  def project
  def straper
  
   @Before
   public void setup(){
      straper = new ProjectStrap()
      project = straper.createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
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
    project.cleanWrapper.execute() 
  }	   
  
}
