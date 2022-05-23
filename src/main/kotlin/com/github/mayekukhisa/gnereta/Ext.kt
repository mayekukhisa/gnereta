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

import freemarker.template.Configuration
import java.io.StringWriter

/**
 * Processes a template named by [pathname] using the [dataModel] provided.
 *
 * @param pathname The name of the path to a template file relative to the base template directory.
 * @param dataModel The holder of the variables visible from the template.
 * @return The generated output as a string.
 */
fun Configuration.renderTemplate(pathname: String, dataModel: Any): String {
   val writer = StringWriter()
   getTemplate(pathname).process(dataModel, writer)
   return writer.toString()
}
