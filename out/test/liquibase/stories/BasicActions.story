import com.kenshoo.test.ProjectStrap

before "create project with liquibase plugin", {
    given 'a project with liquidBase plugin instance', {
        project new ProjectStrap().createProjectWithPlugin(com.kenshoo.liquibase.LiquibasePlugin)
    }
}

scenario 'status report', {
    given 'a clean database', {
    }


}
