package com.kenshoo.liquibase.datasource

import com.kenshoo.liquibase.ClasspathMangler

class DynamicDatasources {

  def createDs(configuration){
    println configuration.type
    def script = new ClasspathMangler().readResourceText("datasources/${configuration.type}.groovy")
    def shell = new GroovyShell(new Binding(configuration))
    shell.evaluate(script)
  }

}
