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
    project.liquidPackage.execute()
    def zipFile = new java.util.zip.ZipFile(new File("${straper.buildDir.path}/${project.liquidPackage.archiveName}"))
    def entries = zipFile.entries().collect {it.name}
    assertThat entries,hasItems('src/') 
    assertThat entries,hasItems("liquibase-standalone-${straper.pluginVersion}.jar".toString()) 
  }	   
 
  
}
