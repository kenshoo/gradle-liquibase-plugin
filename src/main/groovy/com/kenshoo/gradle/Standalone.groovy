package com.kenshoo.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.Convention 
import org.gradle.api.Task


class Standalone{
  
  def plugins = [:]
  def properties = [:]
  def tasks = [:]
  def convention = [getPlugins:{plugins}] as  Convention

  def instance() {
      Project.metaClass.propertyMissing = {name -> 
        if(!properties[name]){
          properties[name]=[:]
	  }
	  properties[name]
      }

      Project.metaClass.propertyMissing = {name,value -> 
         properties[name]=value
      }

      def project = [ 
         getConvention: {convention},
         task: { props,name -> 
                  tasks[name]=props
                  [leftShift: {c->tasks[name].doLast=c;delegate as Task }] as Task
	  }
      ] as Project
  }
}
