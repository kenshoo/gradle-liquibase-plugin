databaseChangeLog() {
  include(file: "src/main/groovy/com/kenshoo/liquibase/1/main.groovy")
  include(file: "src/main/groovy/com/kenshoo/liquibase/2/main.groovy")
}
