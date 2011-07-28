package com.kenshoo.test

import com.kenshoo.liquibase.LiquibaseApiResolver
import org.apache.log4j.Logger
import org.junit.Test
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.internal.matchers.IsCollectionContaining.hasItems

 /**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/6/11
 * Time: 2:03 PM
 */
class ApiResolvingTest {

    def actualStdout = new PrintStream(new FileOutputStream(FileDescriptor.out))

    def resolver = new LiquibaseApiResolver()

    def logger = Logger.getLogger(this.class.name)


    @Test
    public void basicApiWhiteList() {
        def methods = resolver.readAllApiMethods()
        assertThat methods.collect {it.name}, hasItems('update', 'tag', 'rollback')
    }

    @Test
    public void convertMethodsToTasks() {
        def methods = resolver.readAllApiMethods()
        def tasks = resolver.convertMethodToTasks(methods)
        assertThat tasks.keySet(), hasItems('update', 'tag', 'rollback')
        assertThat tasks['update'].paramLists.size(), equalTo(2)
    }
}
