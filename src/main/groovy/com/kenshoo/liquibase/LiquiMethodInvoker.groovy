package com.kenshoo.liquibase

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/7/11
 * Time: 4:55 PM
 */
class LiquiMethodInvoker {
    def matchMaxParams(project, taskMeta) {
        def sortedReverse = taskMeta.paramsWithoutNonProvided().sort {-1 * it.size()}
        def match = sortedReverse.find {list ->
            list.every {param -> project.hasProperty(param.name)}
        }
        if (!match) {
            throw new RuntimeException('Not enough properties provided for task, please read description on how to use.');
        }
        match
    }

    def invoke(project, taskMeta, liquid) {
        def params = matchMaxParams(project, taskMeta)
        def values = params.inject([]) {r, p -> r << project."${p.name}" }
        if(taskMeta.name.equals('reportStatus')){
           values.add(new StringWriter())// the only non user provided param at the moment
        }
        liquid.metaClass.invokeMethod(liquid, taskMeta.name, values as Object[])
    }
}
