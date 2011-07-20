package com.kenshoo.liquibase
import java.net.InetAddress

class HostsAssertion {

   def ipFromName = { host -> InetAddress.getByName(host)}
   def localSubnet = {ipFromName('localhost')}
   def limitedSubnets = ['10.63.9', '10.103.9']

   def assertHostName(destination) {
     limitedSubnets.each { subnet ->
       if(ipFromName(destination).startsWith(subnet) && !localSubnet().startsWith(subnet)){
        throw new Exception("Running liquibase on ${destination} which is under ${subnet} is forbiden!")
	 }
     }
   }

}
