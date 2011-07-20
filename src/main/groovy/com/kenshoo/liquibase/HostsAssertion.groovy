package com.kenshoo.liquibase
import java.net.InetAddress

class HostsAssertion {

    def ipFromName = { host -> InetAddress.getByName(host).hostAddress}
    def limitedSubnets = ['10.63.9.', '10.103.9.']

    def assertHostName(destination) {
	 limitedSubnets.each { subnet ->
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
