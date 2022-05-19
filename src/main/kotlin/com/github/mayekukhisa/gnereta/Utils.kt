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

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import org.apache.commons.io.IOUtils

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
    * Converts a json string to an object.
    *
    * @param deserializer The serializer for reconstructing an object from the given [jsonString].
    * @return An object of type [T].
    */
   fun <T> parseJson(deserializer: DeserializationStrategy<T>, jsonString: () -> String): T {
      return Json.decodeFromString(deserializer, jsonString())
   }
}
