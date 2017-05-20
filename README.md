[![license](https://img.shields.io/badge/gradle-3.3-brightgreen.svg)](https://gradle.org)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/mit-license.php)

#  springboot basic framework integration

 帮助你集成框架 到 Spring Boot。
 
 Help you integrate the framework to Spring Boot.
 
## How to use

### maven

在pom.xml加入nexus资源库（解决中国访问慢的问题,已经加入中央仓库）

Add the following nexus repository(fix china access slow problem,already append to central nexus.)  to your pom.xml:

    <repositories>
        <repository>
            <id>nexus</id>
            <name>nexus</name>
            <url>http://maven.cuisongliu.com/content/groups/public</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

在pom.xml加入依赖

Add the following dependency to your pom.xml:

    <dependency>
       <groupId>com.cuisongliu</groupId>
       <artifactId>springboot</artifactId>
       <version>1.0.0</version>
     </dependency>

### gradle

在build.gradle加入nexus资源库（解决中国访问慢的问题,已经加入中央仓库）

Add the following nexus repository(fix china access slow problem,already append to central nexus.)  to your build.gradle:

    allprojects {
        repositories {
            mavenLocal()
            maven { url "http://maven.cuisongliu.com/content/groups/public" }
            mavenCentral()
            jcenter()
        }
    }
    
在build.gradle加入依赖

Add the following dependency to your build.gradle:
    
    compile "com.cuisongliu:druid-spring-boot-starter:1+"
    
### Depends on the framework and version


| dependencies | version |
| :------|:------|
|spring-boot-starter-web|1.5.3.RELEASE|
|spring-boot-starter-jdbc|1.5.3.RELEASE|
|spring-boot-starter-aop|1.5.3.RELEASE|
|spring-boot-starter-thymeleaf|1.5.3.RELEASE|
|cglib-nodep|3.2.5|
|commons-codec|1.9|
|commons-beanutils-core|1.8.3|
|commons-collections|3.2.2|
|commons-lang|2.6|
|jsoup|1.10.2|
|fastjson|1.2.32|
|mysql-connector-java|5.1.42|
|mybatis-spring-boot-starter|1.3.0|
|mapper-spring-boot-starter|1.1.1|
|pagehelper-spring-boot-starter|1.1.1|
|orderbyhelper-spring-boot-starter|1.0|
|druid-spring-boot-starter|1.0.31.04|

## Example


    spring:
      datasource:
        url: xxx
        username: xxx
        password: xxx
        druid:
              filters: stat,wall,log4j
              connection-properties:
                - druid.stat.mergeSql=true
                - druid.stat.slowSqlMillis=5000
              filter:
                enable: true
                principal-session-name: session_admin
                profile-enable: true
                principal-cookie-name: session_admin
                session-stat-enable: true
              stat:
                enable: true
                aop-type: type,name
                target-bean-type: com.cuisongliu.springboot.core.mapper.MyMapper
                bean-names:
                   - UserMapper
                   - userMapper

## Acknowledgments

 [druid](https://github.com/alibaba/druid).
 [mapper](https://github.com/abel533/Mapper).
 [pagehelper](https://github.com/pagehelper/Mybatis-PageHelper)
 [OrderByHelper](https://github.com/abel533/OrderByHelper)
 [mybatis](https://github.com/mybatis/mybatis-3)