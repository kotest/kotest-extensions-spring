# kotest-extension-spring

### This project has been promoted to the top level Kotest repo. Follow development and raise tickets in that project.

Kotest extension for [Spring](https://spring.io/projects/spring-framework/).

See [docs](https://kotest.io/docs/extensions/spring.html).

Please create issues on the main kotest [board](https://github.com/kotest/kotest/issues).

[![Build Status](https://github.com/kotest/kotest-extensions-spring/workflows/master/badge.svg)](https://github.com/kotest/kotest-extensions-spring/actions)
[<img src="https://img.shields.io/maven-central/v/io.kotest.extensions/kotest-extensions-spring.svg?label=latest%20release"/>](http://search.maven.org/#search|ga|1|kotest-extensions-spring)
![GitHub](https://img.shields.io/github/license/kotest/kotest-extensions-spring)
[![kotest @ kotlinlang.slack.com](https://img.shields.io/static/v1?label=kotlinlang&message=kotest&color=blue&logo=slack)](https://kotlinlang.slack.com/archives/CT0G9SD7Z)
[<img src="https://img.shields.io/nexus/s/https/s01.oss.sonatype.org/io.kotest.extensions/kotest-extensions-spring.svg?label=latest%20snapshot"/>](https://s01.oss.sonatype.org/content/repositories/snapshots/io/kotest/extensions/kotest-extensions-spring/)

## Compatibility
The current version, `1.1.3`, is confirmed to be compatible with Spring Boot 3, Kotest 5.6 and Kotlin 1.8.

See the [kotest-examples-spring-webflux](https://github.com/kotest/kotest-examples-spring-webflux) project for an
example where this extension is used with the most up-to-date versions of Kotest and Spring Boot.

## Changelog

### 1.2.0

* Updated to use Kotest 5.7 and Kotlin 1.8

### 1.1.3

Maintenance release, only updating dependencies
* Gradle 7.6.1
* Spring 5.3.27
* Kotest 5.5.5
* byte-buddy 1.14.4

### 1.1.2

* Fix for Kotest 5.4. Safe to use on all on 5.x versions.

### 1.1.1

* Dynamic tests, for instance using `withData`, now invokes Spring's Test Context before/after between each test case.

### 1.1.0

* Requires Kotest 5.0 and Kotlin 1.6
* Deprecated SpringListener removed
* Supports isolation modes

### 1.0.1

* Support for JDK 8
* Better warning when using a Kotlin [soft-keyword in a package name](https://github.com/kotest/kotest/issues/2489).

### 1.0.0

* Deprecated SpringTestListener in favour of SpringTestExtension
* Migrated from the main Kotest repo to a standalone repo.
