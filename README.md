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
       <artifactId>springboot-puls</artifactId>
       <version>2.0</version>
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
    
    compile "com.cuisongliu:springboot-plus:2+"
    
## Add cuisongliu's springboot to project

    1. add to project configure
    2. application.java extends from SpringMvcConfig
    3. application.java append to @ComponentScan and @MapperScan
    4.  @ComponentScan and @MapperScan not set package com.cuisongliu prefix
    
    1. 在项目中加入项目配置文件
    2. 在springboot 的入口继承SpringMvcConfig
    3. 在入口类加入ComponentScan和MapperScan配置
    4.  @ComponentScan 和 @MapperScan  不能设置为com.cuisongliu开头

> [Example for cuisongliu's springboot](https://github.com/cuisongliu/springboot-integration)

## Acknowledgments

 [druid](https://github.com/alibaba/druid)
 
 [druid-starter](https://github.com/cuisongliu/druid-boot-starter)
 
 [mapper](https://github.com/abel533/Mapper)
 
 [pagehelper](https://github.com/pagehelper/Mybatis-PageHelper)
 
 [OrderByHelper](https://github.com/abel533/OrderByHelper)
 
 [OrderByHelper-starter](https://github.com/cuisongliu/orderbyhelper-boot-starter)
 
 [mybatis](https://github.com/mybatis/mybatis-3)
