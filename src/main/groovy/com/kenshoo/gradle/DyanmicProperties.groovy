package com.kenshoo.gradle

import org.gradle.api.Project

@Singleton
class DyanmicProperties {

  def properties = [:] 

  private DyanmicProperties(){
    Project.metaClass.propertyMissing = {name -> 
	if(!properties[name]){
	  properties[name]=[:]
	}
	properties[name]
    }

    Project.metaClass.propertyMissing = {name,value -> 
	properties[name] = value
    }
  }

}
