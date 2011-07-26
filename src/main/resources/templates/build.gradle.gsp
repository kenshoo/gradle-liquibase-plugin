apply plugin: 'liquibase'

buildscript {
  dependencies {
    classpath 'com.kenshoo.gradle.plugins:liquibase:<%= version%>'
  }
  repositories {
    mavenLocal()
	mavenRepo(name: 'plugins-repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
  }
}

repositories {
  mavenRepo ( name: 'repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
}
