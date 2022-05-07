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
import com.github.mayekukhisa.gnereta.model.TemplatesCatalog
import kotlinx.serialization.json.Json
import org.apache.commons.io.IOUtils

class GneretaCommand : CliktCommand(
   help = "A project generator tool",
   printHelpOnEmptyArgs = true,
) {
   private val listTemplates by option(help = "Show available templates and exit").flag()
   private val version by option(help = "Report tool version and exit").flag()

   override fun run() {
      when {
         listTemplates -> {
            /*
             * TODO: Remove this if-statement later when adding the first template.
             *    The templates list will never be empty.
             */
            if (templates.isEmpty()) {
               echo("No templates found!")
               exit()
            }

            templates.forEach { template -> echo(template) }
            exit()
         }
         version -> {
            echo(VERSION)
            exit()
         }
      }
   }

   companion object {
      const val VERSION = "1.0.0-snapshot"

      /**
       * The list of available templates.
       */
      val templates: List<String>

      init {
         // Read catalog for available templates
         val jsonString = IOUtils.resourceToString(
            "templates/catalog.json",
            Charsets.UTF_8,
            this::class.java.classLoader
         )
         val catalog = Json.decodeFromString(TemplatesCatalog.serializer(), jsonString)
         templates = catalog.entries
      }

      /**
       * Terminates the currently running process.
       *
       * @param status The exit code.
       * @throws ProgramResult
       */
      fun exit(status: Int = 0) {
         throw ProgramResult(status)
      }
   }
}

fun main(args: Array<String>) {
   GneretaCommand()
      .main(args)
}
