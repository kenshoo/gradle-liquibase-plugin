package com.kenshoo.test

import org.junit.Test
import org.junit.Before
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse


class PackagingTest {
  
  def project
  
   @Before
   public void setup(){
      project = new ProjectStrap().createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)

   }
   
  @Test
  public void wrapperCleanup(){
    def wrapperFiles = ['gradlew','gradle']
    project.wrapper.execute() 
    wrapperFiles.each {assertTrue(new File(it).exists())}
    project.cleanWrapper.execute() 
    wrapperFiles.each {assertFalse(new File(it).exists())}
  }

  
}
