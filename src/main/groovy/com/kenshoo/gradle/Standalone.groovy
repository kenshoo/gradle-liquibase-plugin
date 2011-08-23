package com.kenshoo.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.Convention 
import org.gradle.api.Task


class Standalone {
  
  def plugins = [:]
  def tasks = [:]
  def convention = [getPlugins:{plugins}] as  Convention
  def dyanmicProperties = DyanmicProperties.instance.properties
    
  def instance() {
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

