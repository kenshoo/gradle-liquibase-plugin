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

import com.kenshoo.liquibase.LiquiMethodInvoker
import com.kenshoo.liquibase.LiquibaseApiResolver
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/7/11
 * Time: 5:17 PM
 */
class LiquiMethodInvokerTest {

    class Updatable {
        def wasUpdated = false

        def update(String bla) {
            wasUpdated = true
        }
    }

    @Test
    public void findMaxParams() {
        def project = ProjectBuilder.builder().build()
        project.contexts = 'somecontext'
        def resolver = new LiquibaseApiResolver()
        def methods = resolver.readAllApiMethods()
        def tasks = resolver.convertMethodToTasks(methods)

        def oneMatched = new LiquiMethodInvoker().matchMaxParams(project, tasks['update'])
        assertThat oneMatched.size(), equalTo(1)
        assertThat oneMatched[0].name, equalTo('contexts')

        def zeroMatched = new LiquiMethodInvoker().matchMaxParams(project, tasks['validate'])
    }

    @Test
    public void invocation() {
        def project = ProjectBuilder.builder().build()
        project.contexts = 'somecontext'
        def updatable = new Updatable()
        def resolver = new LiquibaseApiResolver()
        def methods = resolver.readAllApiMethods()
        def tasks = resolver.convertMethodToTasks(methods)
        def invoker = new LiquiMethodInvoker()
        invoker.invoke(project, tasks['update'], updatable)
        assertTrue updatable.wasUpdated
    }
}
