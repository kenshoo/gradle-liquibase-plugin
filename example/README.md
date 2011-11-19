# Gradle liquibase plugin example project

The project sturcure is similar to any other projects, under the src folder you can find the database change logs partitioned by version:

```bash
  $ ls src/main/groovy/com/kenshoo/liquibase/
   1  2  all_versions.groovy
```

In order to create a distributable package:

```bash
  $ git submodule update --init
  $ gradle liquidPackage
  $ cd build/libs
  $ unzip liquid-distributable_2.zip
```

Note that the liquid-distributable zip version matches that of the schema version as stated in the build.gradle.
