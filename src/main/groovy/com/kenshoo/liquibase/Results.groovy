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

import liquibase.changelog.ChangeSet
import liquibase.diff.Diff
import liquibase.diff.DiffResult
import org.gradle.api.logging.Logging
import org.slf4j.Logger
import org.slf4j.LoggerFactory

 /**
 * Created by IntelliJ IDEA.
 * User: ronen
 * Date: 4/14/11
 * Time: 6:17 PM
 */
class Results {

    def Logger logger = LoggerFactory.getLogger(this.class);

    def applyResultOutput() {
        Diff.metaClass.summary = {
            DiffResult diffResult = delegate.compare()
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
            PrintStream ps = new PrintStream(baos)
            diffResult.printResult(ps)
            logger.info(Logging.LIFECYCLE, baos.toString('utf-8'))
        }

        ChangeSet.metaClass.summary = {
            logger.info(Logging.LIFECYCLE, delegate.toString())
        }


    }
}
