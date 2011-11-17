# Gradle liquibase plugin
This plugin integrates the use of [liquibase](http://www.liquibase.org/), [gradle](http://gradle.org) and liquibase Groovy [DSL](https://github.com/tlberglund/groovy-liquibase) into one package.

It provides the following feature set:
 * Standard project structure (src/main/groovy for change logs and resources for csv data etc..).
 * A complete packaging solution that includes a bundled standalone liquibase plugin ready to be deployed.
 * Full access to most of liquibase CLI client functionality, gradle tasks prints full description of each task including expected parameter lists.
 * Host black list using subnets (limit the plugin from working against hosts in build time).
 * Easy configuration.  

For more info and usage please follow the [wiki](https://github.com/kenshoo-gradle/gradle-liquibase-plugin/wiki).

## Authors
Development was lead by [Ronen Narkis](http://narkisr.com) and released by [Kenshoo](http://kenshoo.com).

## License
This code is released under the Apache Public License 2.0.

