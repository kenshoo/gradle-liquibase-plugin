apply plugin: 'liquibase'

def hasRepo(name){
  repositories.resolvers.find{it.name.equals(name)}
}

buildscript {
  dependencies {
    classpath 'com.kenshoo.gradle.plugins:liquibase:<%= version%>'
  }	
   repositories {
     if(!repositories.resolvers.find{it.name.equals('MavenLocal')}){
      mavenLocal()
     }
     if(!repositories.resolvers.find{it.name.equals('plugins-repo')}){
	mavenRepo(name: 'plugins-repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
     }
   }
}

if(!repositories.resolvers.find{it.name.equals('repo')}){
 repositories {
   mavenRepo ( name: 'repo', urls: "http://bob:8081/artifactory/repo" ).setSnapshotTimeout(0)
 }
}
