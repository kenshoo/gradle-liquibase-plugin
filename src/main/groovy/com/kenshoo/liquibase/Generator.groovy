package com.kenshoo.liquibase

import groovy.text.SimpleTemplateEngine


class Generator {
  def configurationName = 'liquid.conf'

    def generateConfiguration(project) {
  	def engine = new SimpleTemplateEngine()
  	def binding = ['dbUser', 'dbPass', 'dbHost', 'dbName'].inject([:]) { m,v ->
  	  m[v]=project.hasProperty(v)? project."$v" : '';m
	}
  	  
	def conf = new ClasspathMangler().readResourceText("templates/${configurationName}")  	
	def template = engine.createTemplate(conf).make(binding)
	new File(configurationName).write(template.toString())
    }
}
