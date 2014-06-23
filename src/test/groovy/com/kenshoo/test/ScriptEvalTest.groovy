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
import static org.hamcrest.core.IsCollectionContaining.hasItems
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/5/11
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
class ScriptEvalTest {
    @Test
    public void sanity() {
        def props = new GroovyShell().evaluate(new File('src/test/resources/liquid.conf'))
        assertThat props.dbs[0].keySet(),hasItems('name','user','pass','host')
        assertThat props.dbs[0].values(),hasItems('dba')
    }
}
