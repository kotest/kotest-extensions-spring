import org.gradle.api.tasks.testing.logging.TestExceptionFormat

buildscript {
   repositories {
      jcenter()
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
      }
      maven {
         url = uri("https://plugins.gradle.org/m2/")
      }
   }
}

plugins {
   java
   `java-library`
   signing
   maven
   `maven-publish`
   kotlin("jvm").version(Libs.kotlinVersion)
   id("org.jetbrains.kotlin.plugin.spring").version(Libs.kotlinVersion)
}

allprojects {

   group = Libs.org
   version = Ci.version

   dependencies {
      implementation(kotlin("reflect"))
      implementation(Libs.Kotest.api)
      implementation(Libs.Spring.context)
      implementation(Libs.Spring.test)
      implementation(Libs.Coroutines.coreJvm)
      implementation(Libs.Bytebuddy.bytebuddy)
      testImplementation(Libs.Kotest.junit5)
      testImplementation(Libs.SpringBoot.test)
   }

   tasks.named<Test>("test") {
      useJUnitPlatform()
      testLogging {
         showExceptions = true
         showStandardStreams = true
         exceptionFormat = TestExceptionFormat.FULL
      }
   }

   tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions.jvmTarget = "1.8"
   }

   repositories {
      mavenLocal()
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots")
      }
   }
}

apply("./publish.gradle.kts")
