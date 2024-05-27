package io.kotest.extensions.spring

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestExecutionListener as SpringTestExecutionListener

@TestExecutionListeners(
   DummyTestExecutionListener::class,
   mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@SpringBootTest(classes = [Components::class])
class SpringTestExecutionListenerTest : FunSpec() {

   @Autowired
   lateinit var userService: UserService

   private object IgnoreExceptions : TestCaseExtension {
      override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult =
         execute(testCase).let { result ->
            if (result.isError) TestResult.Ignored
            else result
         }
   }

   override fun extensions() = listOf(
      IgnoreExceptions, // Must be applied before SpringExtension, else it hides the exception from the SpringExtension
      SpringExtension,
   )

   init {
      test("Should autowire with spring listeners") {
         userService.repository.findUser()
      }

      test("Dummy test to test spring listener in afterSpecClass") {
         // Only here to verify counts are incremented
      }

      withData(
         3, 4
      ) {
         // Only here to verify counts are incremented
      }

      context("wrapped withData") {
         withData(
            5, 6, 7
         ) {
            // Only here to verify counts are incremented
         }
      }

      test("Test that throws") {
         throw RuntimeException("Boom")
      }

      context("property tests are not included") {
         checkAll(100, Arb.int()) {
            // Only here to verify counts are _NOT_ incremented
         }
      }

      afterProject {
         DummyTestExecutionListener.beforeTestClass shouldBe 1
         DummyTestExecutionListener.beforeTestMethod shouldBe 8
         DummyTestExecutionListener.beforeTestExecution shouldBe 8
         DummyTestExecutionListener.prepareTestInstance shouldBe 1
         DummyTestExecutionListener.afterTestExecution shouldBe 8
         DummyTestExecutionListener.afterTestMethod shouldBe 8
         DummyTestExecutionListener.afterTestClass shouldBe 1
         DummyTestExecutionListener.testErrors shouldBe 1
      }
   }
}

class DummyTestExecutionListener : SpringTestExecutionListener {

   override fun beforeTestClass(testContext: TestContext) {
      beforeTestClass++
   }

   override fun beforeTestMethod(testContext: TestContext) {
      beforeTestMethod++
   }

   override fun beforeTestExecution(testContext: TestContext) {
      beforeTestExecution++
   }

   override fun prepareTestInstance(testContext: TestContext) {
      prepareTestInstance++
   }

   override fun afterTestExecution(testContext: TestContext) {
      afterTestExecution++
      if (testContext.testException != null) testErrors++
   }

   override fun afterTestMethod(testContext: TestContext) {
      afterTestMethod++
   }

   override fun afterTestClass(testContext: TestContext) {
      afterTestClass++
   }

   companion object {
      var beforeTestClass = 0
      var beforeTestMethod = 0
      var beforeTestExecution = 0
      var prepareTestInstance = 0
      var afterTestExecution = 0
      var testErrors = 0
      var afterTestMethod = 0
      var afterTestClass = 0
   }
}
