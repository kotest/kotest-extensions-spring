package io.kotest.extensions.spring.security

import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.BeforeSpringExtension
import io.kotest.extensions.spring.testContextManager
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.TestExecutionEvent
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.test.context.TestContextAnnotationUtils

class SpringMockUserExtension(
   private val username: String = "user",
   private val password: String = "password",
   private val roles: List<String> = listOf("USER"),
   private val authorities: List<String> = listOf()
) : BeforeSpringExtension, AfterTestListener {

   override suspend fun beforeSpring(testCase: TestCase) {
      TestSecurityContextHolder.setContext(createSecurityContext())
   }

   override suspend fun afterAny(testCase: TestCase, result: TestResult) {
      TestSecurityContextHolder.clearContext()
   }

   /**
    * Copied from Spring's WithMockUserSecurityContextFactory
    */
   private suspend fun createSecurityContext(): SecurityContext {
      val withSecurityContextAnnotationDescriptor =
         TestContextAnnotationUtils.findAnnotationDescriptor(
            WithMockUser::class.java,
            WithSecurityContext::class.java
         )!!.annotation
      val factoryClazz: Class<out WithSecurityContextFactory<out Annotation>> =
         withSecurityContextAnnotationDescriptor.factory.java
      val factory =
         testContextManager().testContext.applicationContext.autowireCapableBeanFactory.createBean(
            factoryClazz
         ) as WithSecurityContextFactory<WithMockUser>
      val withMockUser = WithMockUser(
         value = username,
         username = username,
         roles = roles.toTypedArray(),
         authorities = authorities.toTypedArray(),
         password = password,
         setupBefore = TestExecutionEvent.TEST_METHOD
      )
      return factory.createSecurityContext(withMockUser)
   }
}
