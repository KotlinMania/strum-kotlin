# Strum Kotlin

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/strum-kotlin?color=blue&style=flat-square)](https://central.sonatype.com/artifact/io.github.kotlinmania/strum-kotlin)
[![Build Status](https://img.shields.io/github/actions/workflow/status/KotlinMania/strum-kotlin/ci.yml?branch=main&style=flat-square)](https://github.com/KotlinMania/strum-kotlin/actions)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4%2B-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Platforms](https://img.shields.io/badge/Platforms-JVM%20%7C%20Android%20%7C%20Apple%20%7C%20Linux%20%7C%20Windows%20%7C%20JS%20%7C%20Wasm-orange?style=flat-square)](https://github.com/KotlinMania/strum-kotlin)
[![Swift Export](https://img.shields.io/badge/Swift_Export-Supported-F05138?style=flat-square&logo=swift&logoColor=white)](https://github.com/KotlinMania/strum-kotlin)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

**Strum Kotlin** provides rich tools, annotations, and runtime extensions for working with enums and strings in Kotlin Multiplatform.

Whether you need string parsing with aliases, case conversions (`kebab-case`, `snake_case`, `camelCase`, etc.), variant metadata and documentation, or property lookups, Strum Kotlin brings clean, type-safe enum capabilities across all platforms.

---

## Features

- **Type-Safe String Parsing**: Robust enum parsing (`fromString`, `fromStringOrNull`) supporting custom serializations, aliases, and case-insensitivity.
- **Case Conversion Engine**: Comprehensive `CaseStyle` transformations matching Strum/Heck (`camelCase`, `PascalCase`, `kebab-case`, `snake_case`, `SCREAMING_SNAKE_CASE`, `SCREAMING-KEBAB-CASE`, `lowercase`, `UPPERCASE`, `title_case`, `Train-Case`).
- **Variant Messages & Docs**: Attach user-facing messages, detailed descriptions, and structured documentation to enum variants via `EnumMessage`.
- **Variant Properties**: Store string, numeric, and boolean properties directly with enum entries via `EnumProperty`.
- **Idiomatic Iteration & Counting**: Generic reified helpers for iterating (`enumIter`), counting (`enumCount`), retrieving names (`enumVariantNames`), and ordinal indexing (`fromRepr`).
- **Annotations**: Declarative `@Strum`, `@StrumDiscriminants`, and `@StrumProp` annotations to configure serialization, display formatting, and properties.
- **Swift & Native Interop**: Fully compatible with Kotlin 2.4 Swift Export and Apple SPM / XCFramework packaging.

---

## Installation

Add `strum-kotlin` to your project dependencies:

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.github.kotlinmania:strum-kotlin:0.2.0")
}
```

The runtime artifact does not depend on the derive toolchain. Macro-generation APIs are provided separately by `strum-macros-kotlin`; import their canonical `io.github.kotlinmania.strummacros` packages when using that tooling.

### Version Catalog (`gradle/libs.versions.toml`)

```toml
[versions]
strum = "0.2.0"

[libraries]
strum-kotlin = { module = "io.github.kotlinmania:strum-kotlin", version.ref = "strum" }
```

---

## Quick Start & Usage

### 1. Parsing Enums from Strings

Strum provides `fromString<E>()` and `fromStringOrNull<E>()` to parse enum entries safely:

```kotlin
import io.github.kotlinmania.strum.fromString
import io.github.kotlinmania.strum.fromStringOrNull
import io.github.kotlinmania.strum.ParseException

enum class Status {
    ACTIVE,
    INACTIVE,
    PENDING,
}

// Case-sensitive parsing
val active = fromString<Status>("ACTIVE")

// Case-insensitive parsing
val pending = fromString<Status>("pending", ignoreCase = true)

// Safe nullable parsing
val unknown = fromStringOrNull<Status>("UNKNOWN") // null

// Throws ParseException when not found
try {
    fromString<Status>("INVALID")
} catch (e: ParseException) {
    println("Parse failed: ${e.error}") // ParseError.VARIANT_NOT_FOUND
}
```

### 2. Case Conversion (`CaseStyle`)

Easily convert enum variant names or strings into any supported case style:

```kotlin
import io.github.kotlinmania.strum.CaseStyle

val name = "DarkBlack"

println(CaseStyle.KEBAB_CASE.convert(name))           // "dark-black"
println(CaseStyle.SNAKE_CASE.convert(name))           // "dark_black"
println(CaseStyle.CAMEL_CASE.convert(name))           // "darkBlack"
println(CaseStyle.TITLE_CASE.convert(name))           // "Dark Black"
println(CaseStyle.SCREAMING_SNAKE_CASE.convert(name)) // "DARK_BLACK"
println(CaseStyle.TRAIN_CASE.convert(name))           // "Dark-Black"
```

You can also use `@Strum(serializeAll = "...")` to configure enum formatting:

```kotlin
import io.github.kotlinmania.strum.Strum
import io.github.kotlinmania.strum.CaseStyle

@Strum(serializeAll = "kebab-case")
enum class ThemeColor {
    DARK_BLACK,
    BRIGHT_WHITE;

    override fun toString(): String = CaseStyle.KEBAB_CASE.convert(name)
}

println(ThemeColor.DARK_BLACK) // "dark-black"
```

### 3. Enum Messages & Documentation (`EnumMessage`)

Attach user-facing messages, detailed explanations, and documentation to enum variants:

```kotlin
import io.github.kotlinmania.strum.EnumMessage
import io.github.kotlinmania.strum.StrumEntry
import io.github.kotlinmania.strum.Strum

enum class Pet(
    entry: StrumEntry,
) : EnumMessage by entry {
    @Strum(message = "I have a dog", detailedMessage = "My dog's name is Spots")
    DOG(
        StrumEntry(
            message = "I have a dog",
            detailedMessage = "My dog's name is Spots",
        )
    ),

    /** Documented feline */
    @Strum(message = "I have a cat")
    CAT(
        StrumEntry(
            message = "I have a cat",
            documentation = "Documented feline",
        )
    );
}

val pet = Pet.DOG
println(pet.getMessage())         // "I have a dog"
println(pet.getDetailedMessage()) // "My dog's name is Spots"
```

### 4. Variant Properties (`EnumProperty`)

Store typed metadata alongside enum variants and retrieve them dynamically:

```kotlin
import io.github.kotlinmania.strum.EnumProperty
import io.github.kotlinmania.strum.StrumEntry
import io.github.kotlinmania.strum.Strum

enum class Course(
    entry: StrumEntry,
) : EnumProperty by entry {
    @Strum(props = ["Teacher=Ms. Frizzle", "Room=201", "credits=4", "mandatory=true"])
    SCIENCE(
        StrumEntry(
            properties = mapOf(
                "Teacher" to "Ms. Frizzle",
                "Room" to "201",
                "credits" to 4L,
                "mandatory" to true,
            )
        )
    );
}

val course = Course.SCIENCE
val teacher: String? = course.getStr("Teacher")    // "Ms. Frizzle"
val credits: Long? = course.getInt("credits")      // 4L
val isMandatory: Boolean? = course.getBool("mandatory") // true
```

### 5. Iteration, Counting, and Ordinals

Use reified extension functions for fast, idiomatic access:

```kotlin
import io.github.kotlinmania.strum.enumCount
import io.github.kotlinmania.strum.enumIter
import io.github.kotlinmania.strum.enumVariantArray
import io.github.kotlinmania.strum.enumVariantNames
import io.github.kotlinmania.strum.fromRepr

enum class Priority { LOW, MEDIUM, HIGH }

// Number of variants
val count = enumCount<Priority>() // 3

// Iterate over variants
for (p in enumIter<Priority>()) {
    println(p)
}

// List of variant names
val names = enumVariantNames<Priority>() // ["LOW", "MEDIUM", "HIGH"]

// Array of variants
val array = enumVariantArray<Priority>()

// Lookup by ordinal
val medium = fromRepr<Priority>(1) // Priority.MEDIUM
```

---

## Supported Targets

Strum Kotlin supports all current Kotlin Multiplatform targets:

| Category | Target Platforms |
|---|---|
| **JVM & Android** | JVM (Java 21+), Android (API 24+) |
| **Apple (Darwin)** | macOS (arm64), iOS (arm64, simulator-arm64), tvOS (arm64, simulator-arm64), watchOS (device, simulator) |
| **Native Desktop & Server** | Linux (x64, arm64), Windows (mingw-x64) |
| **Web** | JavaScript (Node.js & Browser), Wasm-JS, Wasm-WASI |

---

## Building & Testing

To compile and verify locally:

```bash
# Run tests across all active targets
./gradlew test

# Full multiplatform build, verification, and lint checks
./gradlew build
```

---

## Provenance & Acknowledgments

`strum-kotlin` is an idiomatic Kotlin Multiplatform port inspired by [`Peternator7/strum`](https://github.com/Peternator7/strum). All original crate concepts belong to Peter Glotfelty and the upstream contributors.

---

## License

This project is licensed under the [MIT License](LICENSE).
Original work Copyright (c) the strum authors.  
Kotlin port Copyright (c) 2026 Sydney Renee and The Solace Project.
