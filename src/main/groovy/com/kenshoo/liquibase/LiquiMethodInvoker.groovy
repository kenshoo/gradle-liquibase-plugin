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
        if (notMatchedAndHasParameters(match, sortedReverse)) {
            throw new RuntimeException('Not enough properties provided for task, please read documentation on how to use (runing gradle tasks).');
        }
        match ?: []
    }



    @SuppressWarnings("GroovyAssignabilityCheck")
    def invoke(project, taskMeta, liquid) {
        def params = matchMaxParams(project, taskMeta)
        def nameToValue = params.inject([:]) {r, p -> r[p.name] = project."${p.name}";r }
        def convertedValues = nameToValue.collect {name, value ->
            def expectedType = params.find {it.name.equals(name)}.type.fullyQualifiedName
            value.asType(Class.forName(expectedType))
        }
        if (taskMeta.name.equals('reportStatus')) {
            convertedValues.add(new StringWriter())// the only non user provided param at the moment
        }
        liquid.metaClass.invokeMethod(liquid, taskMeta.name, convertedValues as Object[])
    }

    private boolean notMatchedAndHasParameters(match, sortedReverse) {
        return !match && sortedReverse
    }
}
