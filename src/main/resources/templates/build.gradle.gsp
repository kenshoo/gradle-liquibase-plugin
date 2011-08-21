apply plugin: 'liquibase'

def hasRepo(name){
  repositories.resolvers.find{it.name.equals(name)}
}

buildscript {
  dependencies {
    classpath 'com.kenshoo.gradle.plugins:liquibase:<%= version%>'
  }	
   repositories {
     flatDir name:'libs' , dirs:projectDir
   }
}
