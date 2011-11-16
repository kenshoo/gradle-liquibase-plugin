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
package com.kenshoo.liquibase

import com.thoughtworks.qdox.JavaDocBuilder
import com.thoughtworks.qdox.model.ClassLibrary
import groovy.text.SimpleTemplateEngine

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/6/11
 * Time: 1:35 PM
 */
class LiquibaseApiResolver {

    public class TaskMeta {
        def name

        def paramLists = []

        def methodDesc

        def hasDryRun = {['update','rollback','changelogSync'].contains(name)} // sql is output to stdout

        def notProvidedFilter = {list -> list.findAll {it.name != 'out'}}

        TaskMeta(name) {
            this.name = name
        }

        def desc() {
            def engine = new SimpleTemplateEngine()
            def binding = [name: name, paramLists: paramsWithoutNonProvided(), methodDesc: methodDesc]
            def text = new ClasspathMangler().readResourceText('description.gsp')
            def template = engine.createTemplate(text).make(binding)
            template.toString()
        }

        def paramsWithoutNonProvided() {
            paramLists.inject([]) {res, list ->
                res << notProvidedFilter(list)
            }
        }


    }

    def readAllApiMethods() {
        JavaDocBuilder builder = new JavaDocBuilder()
        ClassLibrary lib = builder.getClassLibrary()
        lib.addClassLoader(this.class.classLoader.parent)
        lib.addSource(createTempApiFile())
        lib.getJavaClass('com.kenshoo.liquibase.LiquibaseApi').methods
    }

    def convertMethodToTasks(methods) {
        def nameToTask = methods.inject(new HashSet()) {r, m -> r << m.name}.inject([:]) {r, name -> r[name] = new TaskMeta(name); r}
        methods.groupBy {it.name}.each {name, implementations ->
            implementations.collect {it.parameters}.findAll {it}.each { paramList ->
                nameToTask[name].paramLists << paramList
            }
        }
        methods.groupBy {it.name}.each {name, implementations ->
            nameToTask[name].methodDesc = implementations.first().comment
        }
        nameToTask
    }

    def createTempApiFile() {
        File tmp = File.createTempFile("LiquidApi", ".java")
        tmp.deleteOnExit()
        tmp.write(this.class.classLoader.getResourceAsStream('com/kenshoo/liquibase/LiquibaseApi.java').text)
        tmp
    }
}
