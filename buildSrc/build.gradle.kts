import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
   gradlePluginPortal()
}

plugins {
   `kotlin-dsl`
}

java {
   targetCompatibility = JavaVersion.VERSION_1_8
   sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
   kotlinOptions.jvmTarget = "1.8"
}
