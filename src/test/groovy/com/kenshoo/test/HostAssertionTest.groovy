package com.kenshoo.text

import org.junit.Test
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import com.kenshoo.liquibase.HostsAssertion


class HostsAssertionTest {
  
  @Test(expected = Exception.class)
  public void sanity(){
    def assertion = new HostsAssertion()
    assertion.limitedSubnets = ['192.168.1']
    assertion.localSubnet = {'192.168.4.16'}
    assertion.ipFromName = {host -> '192.168.1.1'}
    assertion.assertHostName('bla')
  }
  
}
