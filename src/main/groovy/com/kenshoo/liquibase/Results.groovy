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
import liquibase.diff.Difference
import liquibase.diff.DiffResult
import liquibase.diff.output.report.DiffToReport
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
        Difference.metaClass.summary = {
            DiffResult diffResult = delegate.compare()
            new DiffToReport(diffResult, System.out).print();
            logger.info(Logging.LIFECYCLE, baos.toString('utf-8'))
        }

        ChangeSet.metaClass.summary = {
            logger.info(Logging.LIFECYCLE, delegate.toString())
        }


    }
}
