package com.kenshoo.test

import com.kenshoo.liquibase.LiquidStrap
import com.kenshoo.liquibase.Configuration
import groovy.sql.Sql
import org.junit.Before
import org.junit.After
import org.junit.AfterClass
import static org.junit.Assert.*
import org.junit.Test


/**
 * User: ronen
 * Date: 4/5/11
 * Time: 6:18 PM
 */
class BasicActionsTest {

    def project
    def ds
    def tag = UUID.randomUUID().toString()

    static class DatasourceCreator {
       def configurationScript = 'src/test/resources/liquid.conf'

       def createDs (){
         def configuration = new Configuration(configurationScript)
         def strap = new LiquidStrap()
         configuration.dbs[0].with {
          strap.createDs(user, pass, host, name)
         }
	 }
    }

    @Before
    public void setup() {
        project = new ProjectStrap().createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
        def liqui = project.convention.plugins.liqui
        liqui.configurationScript = 'src/test/resources/liquid.conf'
        ds = new DatasourceCreator().createDs()
     }

    // @AfterClass
    public static void cleanup() {
        def sql = new Sql(new DatasourceCreator().createDs())
        ['drop database if exists liquid_dev', 'create database liquid_dev'].each {
            sql.execute(it)
        }
    }

    

    @Test
    public void reporting() {
        project.verbose = true
        project.contexts = ""
        project.reportStatus.execute()
    }

    @Test
    public void tag() {
        project.tagString = tag
        project.tag.execute()
    }


    @Test
    public void rollback() {
        project.contexts = ""
        project.changesToRollback = 1
        project.rollback.execute()
    }

    @Test
    public void update() {
        project.contexts = ""
        project.update.execute()
        def sql = new Sql(ds)
        def rows = sql.rows('select * from play')
        assertEquals(1,rows.size())
        assertEquals(1,rows.first()['id'])
    }


    @Test
    public void validate() {
        project.validate.execute()
    }
}

