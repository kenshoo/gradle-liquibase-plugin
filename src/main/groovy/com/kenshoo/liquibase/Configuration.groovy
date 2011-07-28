package com.kenshoo.liquibase

class Configuration {

  def dbs
    def defaults

    def Configuration(configurationScript) {
	def conf = new GroovyShell().evaluate(new File(configurationScript))
	  dbs = conf.dbs
	  defaults = conf.defaults
    }

  def applyDefaults(project,taskMeta){
    def params = taskMeta.paramsWithoutNonProvided().inject([] as Set){set,list->
	list.each {param -> set << param.name }
	set
    }

    if(defaults){
	defaults.keySet().intersect(params).each{d -> 
	  if(!project.hasProperty(d)){
	    project."$d"=defaults."$d" 
	  }
	}
    }
  }


}
