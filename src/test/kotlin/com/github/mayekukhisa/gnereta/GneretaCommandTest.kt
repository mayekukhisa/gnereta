/*
 * Copyright (c) 2022 Mayeku Khisa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mayekukhisa.gnereta

import com.github.ajalt.clikt.core.NoSuchOption
import com.github.ajalt.clikt.core.ProgramResult
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class GneretaCommandTest {
   private val stdout = System.out
   private val tempStdout = ByteArrayOutputStream()

   private val classUnderTest = GneretaCommand()

   @BeforeTest
   fun setUP() {
      System.setOut(PrintStream(tempStdout))
   }

   @AfterTest
   fun tearDown() {
      System.setOut(stdout)
   }

   @Test
   fun `test invalid args`() {
      assertFailsWith<NoSuchOption> { classUnderTest.parse(arrayOf("--invalid-option")) }
   }

   @Test
   fun `test version`() {
      assertFailsWith<ProgramResult> { classUnderTest.parse(arrayOf("--version")) }
      assertEquals("1.0.0-snapshot", tempStdout.toString().trim())
   }
}
