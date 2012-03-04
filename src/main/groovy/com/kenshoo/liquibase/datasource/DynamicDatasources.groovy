package com.kenshoo.liquibase.datasource

import com.kenshoo.liquibase.ClasspathMangler

class DynamicDatasources {

  def createDs(configuration){
    if(!configuration.type){
    	throw new Exception('no database type provided in configuration')
    }
    def script = new ClasspathMangler().readResourceText("datasources/${configuration.type}.groovy")
    def shell = new GroovyShell(new Binding(configuration))
    shell.evaluate(script)
  }

}
