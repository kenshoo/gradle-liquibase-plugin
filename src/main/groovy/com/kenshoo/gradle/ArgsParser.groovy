 /*
* Copyright 2011 Kenshoo.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/  
package com.kenshoo.gradle

class ArgsParser {

  def apply(project,args) {
    if(args.size()>1){
	args[1..-1].each {
        def match = (it =~ /\-P(\w*)=(.*)/)
        if(match.size()<1){
          throw new RuntimeException('Wrong argument passed, all arguments to a task should follow -Pname=value form')
	  }
	  def (name,value) = match[0][1..2]
	  project."$name" = value.replace('\"','')
	}
    }
  }

}
