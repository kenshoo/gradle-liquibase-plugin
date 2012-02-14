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

import com.kenshoo.liquibase.LiquibaseApiResolver
// import org.apache.log4j.Logger
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
