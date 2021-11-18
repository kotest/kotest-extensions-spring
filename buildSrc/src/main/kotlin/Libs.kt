object Libs {

   const val kotlinVersion = "1.6.0"
   const val org = "io.kotest.extensions"

   object Kotest {
      private const val version = "5.0.0.RC"
      const val api = "io.kotest:kotest-framework-api:$version"
      const val junit5 = "io.kotest:kotest-runner-junit5-jvm:$version"
   }

   object Spring {
      private const val version = "5.2.15.RELEASE"
      const val context = "org.springframework:spring-context:$version"
      const val test = "org.springframework:spring-test:$version"
   }

   object SpringBoot {
      private const val version = "2.5.4"
      const val test = "org.springframework.boot:spring-boot-starter-test:$version"
   }

   object Bytebuddy {
      const val bytebuddy = "net.bytebuddy:byte-buddy:1.12.1"
   }

   object Coroutines {
      private const val version = "1.5.2"
      const val coreCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val coreJvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$version"
   }
}
