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

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class GneretaCommand : CliktCommand(
   help = "A project generator tool",
   printHelpOnEmptyArgs = true,
) {
   private val version by option(help = "Report tool version and exit").flag()

   override fun run() {
      if (version) {
         echo(VERSION)
         throw ProgramResult(0)
      }
   }

   companion object {
      const val VERSION = "1.0.0-snapshot"
   }
}

fun main(args: Array<String>) {
   GneretaCommand()
      .main(args)
}