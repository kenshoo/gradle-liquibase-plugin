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
package com.kenshoo.liquibase
import java.net.InetAddress
import groovy.util.Eval

class HostsAssertion {

    def ipFromName = { host -> InetAddress.getByName(host).hostAddress}

    def assertHostName(destination) {
       def script = new ClasspathMangler().readResourceText('LimitedSubnets.groovy')
       def limitedSubnets = Eval.me(script)
	 limitedSubnets?.each { subnet ->
		if(ipFromName(destination).startsWith(subnet) && !localAddresses().every{it.startsWith(subnet)}){
		   throw new Exception("Running liquibase on ${destination} which is under ${subnet}% subnet is forbiden!")
		}
	 }
    }

    def localAddresses = {
	  NetworkInterface.getNetworkInterfaces().collect{interfaze ->
		interfaze.inetAddresses.find { it.hostAddress.indexOf(":")==-1}
	  }.findAll { ip -> (ip && ip.isSiteLocalAddress() && !ip.isLoopbackAddress()) }.collect {it.hostAddress}
    }
}
