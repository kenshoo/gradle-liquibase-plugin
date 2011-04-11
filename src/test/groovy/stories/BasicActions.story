import com.kenshoo.test.ProjectStrap
import com.kenshoo.db.setup.NewDbCreatorPlugin
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource
import groovy.sql.Sql

def ds = new MysqlDataSource(user: 'kenshoo_dba', password: 'V0ll3y', serverName: 'fiona')

before "create project with liquibase plugin", {

}

scenario 'status report', {
//    given 'a project with liquidBase plugin instance', {
//        project = new ProjectStrap().createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
//        project.convention.plugins.liqui.configurationScript = 'src/test/resources/liquid.conf'
//    }
//    given 'a clean database', {
//        new NewDbCreatorPlugin(sql: new Sql(ds))
//    }
//    when 'running report' , {
//        project.liquiReport.execute()
    }
}
