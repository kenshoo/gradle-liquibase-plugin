# Gradle liquibase plugin
This plugin integrates [gradle](http://gradle.org), [liquibase](http://www.liquibase.org/) and the liquibase Groovy [DSL](https://github.com/tlberglund/groovy-liquibase), it offers a complete life cycle management of database schemas from build to production.

## It provides the following feature set:

 * Standard project structure (src/main/groovy for change logs and resources for csv data etc..).
 * A complete packaging solution that includes a bundled standalone liquibase plugin ready to be deployed.
 * Full access to most of liquibase CLI client functionality, gradle tasks print full description including expected parameter lists.
 * Hosts black list, limiting the plugin from working against certain hosts (defined in build time).
 * Easy configuration of multiple destination databases and the ability to define default values for tasks. 

For more info and usage please follow the [wiki](https://github.com/kenshoo-gradle/gradle-liquibase-plugin/wiki).

## Authors
Development was lead by [Ronen Narkis](http://narkisr.com) and released by [Kenshoo](http://kenshoo.com).

## License
This code is released under the Apache Public License 2.0.

