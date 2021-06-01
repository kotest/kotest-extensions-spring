object Libs {

   const val kotlinVersion = "1.4.32"
   const val org = "io.kotest.extensions"

   object Kotest {
      private const val version = "4.4.3"
      const val api = "io.kotest:kotest-framework-api:$version"
      const val engine = "io.kotest:kotest-framework-engine:$version"
      const val junit5 = "io.kotest:kotest-runner-junit5-jvm:$version"
   }

   object Spring {
      private const val version = "5.2.15.RELEASE"
      const val context = "org.springframework:spring-context:$version"
      const val test = "org.springframework:spring-test:$version"
   }

   object Bytebuddy {
      const val bytebuddy = "net.bytebuddy:byte-buddy:1.10.22"
   }

   object Coroutines {
      private const val version = "1.4.3"
      const val coreCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val coreJvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$version"
   }
}
