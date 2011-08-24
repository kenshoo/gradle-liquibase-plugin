package com.kenshoo.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.Convention 
import org.gradle.api.Task
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Standalone {
  
  def Logger logger = LoggerFactory.getLogger(this.class);

  def plugins = [:]
  def tasks = [tasks:[:]]
  def printTasks = {
    tasks.each { name,props->
      println("${name} - ${props.description}")    
    }
  }
  def convention = [getPlugins:{plugins}] as  Convention
  def dyanmicProperties = DyanmicProperties.instance.properties
    
  def instance() {
    tasks['tasks'].doLast = printTasks
    tasks.tasks.description = "tasks - Prints the list of available tasks and input parameters.\n"
    def project = [ 
	getConvention: {convention},
	task: { props,name -> 
  	    tasks[name]=props
	    def task = [leftShift: {c->
		tasks[name].doLast=c
		delegate as Task 
	    }] 
	  task as Task
	},
      hasProperty:{name ->  dyanmicProperties[name]!=null}
    ] as Project
  }

}

