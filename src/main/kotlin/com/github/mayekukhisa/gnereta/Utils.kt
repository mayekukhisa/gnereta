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

import com.github.mayekukhisa.gnereta.model.TemplateManifest
import com.github.mayekukhisa.gnereta.model.projects.ProjectModel
import freemarker.template.Configuration
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.util.Locale

/**
 * Provides static utility functions for I/O operations.
 */
object Utils {

   /**
    * Gets the contents of a resource as a string.
    *
    * @param name The resource name.
    * @return The requested string.
    */
   fun resToString(name: String): String {
      return IOUtils.resourceToString(name, Charsets.UTF_8, javaClass.classLoader)
   }

   /**
    * Creates the directory named by [pathname] including any nonexistent parent directories.
    *
    * @param pathname The name of the path where to create the directory.
    */
   private fun mkdirs(pathname: String) {
      if (!File(pathname).mkdirs()) {
         System.err.println("Error: Cannot create directory: \"$pathname\"")
         System.err.println("       File exists or permission denied")
         GneretaCommand.exit(1)
      }
   }

   /**
    * Converts a json string to an object.
    *
    * @param deserializer The serializer for reconstructing an object from the given [jsonString].
    * @return An object of type [T].
    */
   fun <T> parseJson(deserializer: DeserializationStrategy<T>, jsonString: () -> String): T {
      return Json.decodeFromString(deserializer, jsonString())
   }

   /**
    * Creates a project from a template.
    *
    * @param templateName The name of the template to use.
    * @param projectModel The holder of the variables visible from the template.
    */
   fun createProject(templateName: String, projectModel: ProjectModel) {
      mkdirs(projectModel.projectDir)

      // Configure FreeMarker
      val cfg = Configuration(Configuration.VERSION_2_3_31).apply {
         setClassLoaderForTemplateLoading(javaClass.classLoader, "templates")
         defaultEncoding = Charsets.UTF_8.toString()
         locale = Locale.US
      }

      // Load the template's manifest
      val manifest = parseJson(TemplateManifest.serializer()) {
         cfg.renderTemplate("$templateName/manifest.json", projectModel)
      }

      // Create project files
      manifest.texts.forEach { file ->
         FileUtils.writeStringToFile(
            File(file.dest),
            cfg.renderTemplate(file.src, projectModel),
            Charsets.UTF_8,
         )
      }
   }
}
