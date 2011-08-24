package com.kenshoo.gradle

class ArgsParser {

  def apply(project,args) {
    if(args.size()>1){
	args[1..-1].each {
	  def (name,value) = (it =~ /\-P(\w*)=(\w*)/)[0][1..2]
	    project."$name" = value
	}
    }
  }

}
