package com.kenshoo.liquibase

class Generator {
  def configurationName = 'liquid.conf'

  def generateConfiguration() {
     def conf = new ClasspathMangler().readResourceText("templates/${configurationName}")  	
     new File(configurationName).write(conf)
  }
}
