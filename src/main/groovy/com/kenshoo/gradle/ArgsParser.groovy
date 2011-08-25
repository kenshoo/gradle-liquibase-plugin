package com.kenshoo.gradle

class ArgsParser {

  def apply(project,args) {
    if(args.size()>1){
	args[1..-1].each {
        def match = (it =~ /\-P(\w*)=(\w*)/)
        if(match.size()<1){
          throw new RuntimeException('Wrong argument passed, all arguments to a task should follow -Pname=value form')
	  }
	  def (name,value) = match[0][1..2]
	  project."$name" = value
	}
    }
  }

}
