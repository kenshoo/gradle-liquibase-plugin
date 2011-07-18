package com.kenshoo.test

import org.junit.Before
import org.junit.After
import org.junit.Test
import com.kenshoo.liquibase.*
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
 



class GeneratorTest {
 
 def actualStdout = new PrintStream(new FileOutputStream(FileDescriptor.out))
 @Test
 public void confGeneration(){
    def gen = new Generator()
    gen.generateConfiguration()
    actualStdout.println new File(gen.configurationName).text.class
    def expected = "[user: '', pass: '', host: '1', name: '', changeLog: 'src/main/groovy/com/kenshoo/liquibase/349/main.groovy']"
    assertThat new File(gen.configurationName).text, containsString(expected)
 }

}
