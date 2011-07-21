package com.kenshoo.test

import org.junit.Before
import org.junit.After
import org.junit.Test
import com.kenshoo.liquibase.*
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
 



class GeneratorTest {


 @After
 public void cleanup(){
  new File('liquid.conf').delete()
 }
 
 @Test
 public void confGeneration(){
    def gen = new Generator()
    gen.generateConfiguration()
    def expected = "[user: '', pass: '', host: '', name: '', changeLog: 'src/main/groovy/com/kenshoo/liquibase/349/main.groovy']"
    assertThat new File(gen.configurationName).text.trim(), containsString(expected)
 }

}
