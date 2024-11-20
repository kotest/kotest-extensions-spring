package io.kotest.extensions.spring

import io.kotest.core.extensions.Extension
import io.kotest.core.test.TestCase

internal interface BeforeSpringExtension: Extension {

   /**
    * Called before testContextManager().beforeTestMethod() in SpringTestExtension
    */
   suspend fun beforeSpring(testCase: TestCase): Unit = Unit

}
