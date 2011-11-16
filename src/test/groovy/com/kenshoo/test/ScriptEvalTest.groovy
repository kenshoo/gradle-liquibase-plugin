package com.kenshoo.test

import org.junit.Test
import static org.junit.internal.matchers.IsCollectionContaining.hasItems
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
