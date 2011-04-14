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
