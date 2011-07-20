package com.kenshoo.text

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
     assertThat ips.first(),containsString('192.168.')
  }

  @Test(expected = Exception.class)
  public void sanity(){
    def assertion = new HostsAssertion()
    assertion.limitedSubnets = ['192.168.1']
    assertion.localAddresses= {['192.168.4.16']}
    assertion.ipFromName = {host -> '192.168.1.1'}
    assertion.assertHostName('bla')
  }
  
}
