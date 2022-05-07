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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   val kotlinVersion = "1.6.21"

   kotlin("jvm") version kotlinVersion
   kotlin("plugin.serialization") version kotlinVersion
   application
}

group = "com.github.mayekukhisa.gnereta"
version = "1.0.0-snapshot"

repositories {
   mavenCentral()
}

dependencies {
   implementation("com.github.ajalt.clikt:clikt:3.4.2")
   implementation("commons-io:commons-io:2.11.0")
   implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

   testImplementation(kotlin("test"))
   testImplementation(kotlin("test-junit5"))
}

tasks.test {
   useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
   kotlinOptions.jvmTarget = "11"
}

application {
   mainClass.set("${group}.MainKt")
}
