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

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.parser.ChangeLogParserFactory
import liquibase.parser.ext.GroovyLiquibaseChangeLogParser
import liquibase.resource.FileSystemResourceAccessor
import com.kenshoo.liquibase.datasource.DynamicDatasources
import org.gradle.api.Project

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 3:25 PM
 */
class LiquidStrap {


    def Liquibase build(configuration, project) {
        ChangeLogParserFactory.getInstance().register(new GroovyLiquibaseChangeLogParser())
        def ds = new DynamicDatasources().createDs(configuration)
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(ds.getConnection()))
        FileSystemResourceAccessor accessor = getFileSystemResourceAccessor(project)
        accessor.toClassLoader()
        new Liquibase(configuration.changeLog, accessor, database)
    }

    FileSystemResourceAccessor getFileSystemResourceAccessor(Project project) {
        return new FileSystemResourceAccessor() {
            @Override
            public ClassLoader toClassLoader() {
                try {
                    return new URLClassLoader(getUrls());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }

            private URL[] getUrls() {
                ArrayList<URL> urls = new ArrayList<>();
                this.class.classLoader.getURLs().each {URL url ->
                        urls.add(url)
                }
                return  urls.toArray()
            }
        }
    }
}
