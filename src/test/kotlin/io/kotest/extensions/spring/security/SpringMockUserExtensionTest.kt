package io.kotest.extensions.spring.security

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.descriptors.append
import io.kotest.core.descriptors.toDescriptor
import io.kotest.core.names.TestName
import io.kotest.core.source.sourceRef
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestType
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class SpringMockUserExtensionTest : FunSpec() {

   override fun extensions() = listOf(SpringExtension)

   init {

      afterAny {
         SecurityContextHolder.getContext().authentication = null
      }

      test("should set user before Spring in Spring security context") {
         SpringMockUserExtension().beforeSpring(
            TestCase(
               descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
               name = TestName("name"),
               spec = this@SpringMockUserExtensionTest,
               test = {},
               source = sourceRef(),
               type = TestType.Test
            )
         )

         val expectedAuthorities = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
         val expectedPrincipal = User("user", "password", true, true, true, true, expectedAuthorities)
         val expectedAuthentication = UsernamePasswordAuthenticationToken.authenticated(
            expectedPrincipal,
            expectedPrincipal.password,
            expectedPrincipal.authorities
         )
         SecurityContextHolder.getContext().authentication shouldBeEqual expectedAuthentication
         TestSecurityContextHolder.getContext().authentication shouldBeEqual expectedAuthentication
      }

      test("should remove user after any from Spring security context") {
         SecurityContextHolder.getContext().authentication =
            PreAuthenticatedAuthenticationToken(
               null,
               null,
               emptyList()
            )
         SpringMockUserExtension().afterAny(
            TestCase(
               descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
               name = TestName("name"),
               spec = this@SpringMockUserExtensionTest,
               test = {},
               source = sourceRef(),
               type = TestType.Test
            ),
            TestResult.Ignored
         )

         SecurityContextHolder.getContext().authentication shouldBe null
         TestSecurityContextHolder.getContext().authentication shouldBe null
      }

      test("should assign roles to authentication") {
         SpringMockUserExtension(roles = listOf("TEST")).beforeSpring(
            TestCase(
               descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
               name = TestName("name"),
               spec = this@SpringMockUserExtensionTest,
               test = {},
               source = sourceRef(),
               type = TestType.Test
            )
         )

         SecurityContextHolder.getContext().authentication.authorities shouldContainOnly listOf(SimpleGrantedAuthority("ROLE_TEST"))
      }


      test("should reject roles starting with ROLE") {
         shouldThrow<IllegalArgumentException> {
            SpringMockUserExtension(roles = listOf("ROLE_TEST")).beforeSpring(
               TestCase(
                  descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
                  name = TestName("name"),
                  spec = this@SpringMockUserExtensionTest,
                  test = {},
                  source = sourceRef(),
                  type = TestType.Test
               )
            )
         }
      }

      test("should assign authorities to authentication") {
         SpringMockUserExtension(authorities = listOf("ADMIN")).beforeSpring(
            TestCase(
               descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
               name = TestName("name"),
               spec = this@SpringMockUserExtensionTest,
               test = {},
               source = sourceRef(),
               type = TestType.Test
            )
         )

         SecurityContextHolder.getContext().authentication.authorities shouldContainOnly listOf(SimpleGrantedAuthority("ADMIN"))
      }

      test("should not assign roles and authorities") {
         shouldThrow<IllegalStateException> {
            SpringMockUserExtension(roles = listOf("ROLE_TEST"), authorities = listOf("ADMIN")).beforeSpring(
               TestCase(
                  descriptor = SpringMockUserExtensionTest::class.toDescriptor().append("aaa"),
                  name = TestName("name"),
                  spec = this@SpringMockUserExtensionTest,
                  test = {},
                  source = sourceRef(),
                  type = TestType.Test
               )
            )
         }
      }
   }
}

