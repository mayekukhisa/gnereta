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
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.mayekukhisa.gnereta.model.Template
import com.github.mayekukhisa.gnereta.model.TemplatesCatalog
import com.github.mayekukhisa.gnereta.model.projects.BasicProject

class GneretaCommand : CliktCommand(
   help = "A project generator tool",
   printHelpOnEmptyArgs = true,
   invokeWithoutSubcommand = true,
) {
   private val listTemplates by option(help = "Show available templates and exit").flag()
   private val version by option(help = "Report tool version and exit").flag()

   override fun run() {
      when {
         listTemplates -> {
            templates.forEach { template -> echo(template.name) }
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
      val templates: List<Template>

      init {
         // Read catalog for available templates
         val catalog = Utils.parseJson(TemplatesCatalog.serializer()) {
            Utils.resToString("templates/catalog.json")
         }
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

class CreateCommand : CliktCommand(
   help = "Create a new project",
) {
   // For simplicity, keep the "blank" template as the first entry in the templates catalog
   private val defaultTemplate = GneretaCommand.templates.first()
   private val template by option(
      "-t",
      "--template",
      metavar = "TEMPLATE",
      help = "The template to use",
   ).convert { value ->
      GneretaCommand.templates.find { template -> template.name == value }
         ?: fail("Template not found")
   }.default(defaultTemplate, defaultTemplate.name)

   private val directory by argument().path().convert { value -> value.toFile().canonicalPath }

   init {
      context {
         helpFormatter = CliktHelpFormatter(showDefaultValues = true)
      }
   }

   override fun run() {
      val projectModel = BasicProject(directory)
      Utils.createProject(template.name, projectModel)
   }
}

fun main(args: Array<String>) {
   GneretaCommand()
      .subcommands(CreateCommand())
      .main(args)
}
