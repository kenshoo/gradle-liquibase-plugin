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
package com.kenshoo.test

import org.junit.Test
import static org.hamcrest.CoreMatchers.containsString
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.junit.matchers.JUnitMatchers.*
import com.kenshoo.liquibase.HostsAssertion


class HostsAssertionTest {
  
  
  @Test
  public void localAddress(){
     def ips = new HostsAssertion().localAddresses()  
     def gatewayLine = "netstat -rn".execute().text.split('\n').find {it.startsWith('0.0.0.0')}
     def gateway = (gatewayLine =~ /(\d+.\d+.\d+.\d+)/)[1][0]
     def prefix = (gateway =~ /\d+./)[0]
     assertThat 'local ip is '+ips.first(),ips.first(),containsString(prefix)
  }

  @Test(expected = Exception.class)
  public void sanity(){
    def assertion = new HostsAssertion()
    assertion.localAddresses= {['192.168.4.16']}
    assertion.ipFromName = {host -> '192.168.1.1'}
    assertion.assertHostName('bla')
  }
  
}
