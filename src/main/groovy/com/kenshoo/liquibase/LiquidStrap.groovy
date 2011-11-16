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

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.parser.ChangeLogParserFactory
import liquibase.parser.ext.GroovyLiquibaseChangeLogParser
import liquibase.resource.FileSystemResourceAccessor

/**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/4/11
 * Time: 3:25 PM
 */
class LiquidStrap {


    def Liquibase build(configuration) {
        configuration.with {
            ChangeLogParserFactory.getInstance().register(new GroovyLiquibaseChangeLogParser())
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(createDs(user,pass,host,name).getConnection()))
            new Liquibase(changeLog, new FileSystemResourceAccessor(), database)
        }
    }

    def createDs(user, pass, host, name) {
        new MysqlDataSource(user: user, password: pass, serverName: host, databaseName: name)
    }

}
