# gradle-database

The gradle-database is the simple plugin for gradle for operation with database.

## Getting Started

The project is in versione 0.6 beta and this is reference on the [site gradle](https://plugins.gradle.org/plugin/io.vincentpalazzo.gradledatabase)

- Import the dependence
  ```
    buildscript {
        repositories {
          maven {
            url "https://plugins.gradle.org/m2/"
          }
        }
        dependencies {
          classpath "gradle.plugin.gradleDatabase:database-gradle-plugins:0.6"
        }
      }
      
      apply plugin: "io.vincentpalazzo.gradledatabase"
  ```
- Configure the plugin with data of the Database
  ```
  settingDatabase {
    url = 'yourUrl'
    username = 'yourUsername'
    password = 'yourPassword'
    nameDatabase = 'nameDb'
    driver = 'package driver' //an example org.postgresql.Driver
    nameJar = 'name dependence' //an example org.postgresql:postgresql:42.2.5
    pathFile = 'src/main/resources/db/schema.sql'
    pathFileInsert = 'src/main/resources/db/insert.sql'
    levelLog = 'info'
  }
  ```
  *The loggin is into version alpha and exist level info, debug and error 
  but the print message for only level selected, an example the level debug print only
   message debug and not message info, this is temporary*
  
- Now you can call this task
  - createDatabase
  - createTable
  - insertValue
  - deleteDatabase

## Built With

* [sqltool](https://mvnrepository.com/artifact/org.hsqldb/sqltool/2.2.8) - The framework for some operation sql
* [gradle](https://gradle.org/) - Dependency Management
* [JDBC](https://www.oracle.com/technetwork/java/javase/jdbc/index.html) - The framework for some operation sql
* [jarexec.plugin](https://github.com/lingocoder/jarexec.plugin) - The plugin for find dependency into project main
 
## Authors

* **Vincent Palazzo** - *Initial work* - [Site](https://vincenzopalazzo.github.io/)


## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/vincenzopalazzo/gradle-database/blob/master/LICENSE) file for details

## Appendix

This plugin is testing only Postgress but if you test this plugin with another Database, I'm appy to recive a pull request  with list
update

## DB Tested
- Postgres


