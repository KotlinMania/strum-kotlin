pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
dependencyResolutionManagement {
    repositories {
        exclusiveContent {
            forRepository { maven { url = uri(providers.gradleProperty("strum.stagingRepo").get()) } }
            filter {
                includeModule("io.github.kotlinmania", "strum-kotlin")
                includeModule("io.github.kotlinmania", "strum-kotlin-android")
            }
        }
        google()
        mavenCentral()
    }
}
rootProject.name = "strum-publication-consumer"
