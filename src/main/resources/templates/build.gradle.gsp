apply plugin: 'liquibase'

buildscript {
  dependencies {
    classpath 'com.kenshoo.gradle.plugins:liquibase:<%= version%>'
  }
}
