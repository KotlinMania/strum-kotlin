import java.security.MessageDigest
plugins {
    kotlin("multiplatform") version "2.4.20"
    id("com.android.kotlin.multiplatform.library") version "9.4.1"
}
val producerVersion = providers.gradleProperty("strum.version").get()
check(Regex("[0-9]+\\.[0-9]+\\.[0-9]+").matches(producerVersion))
kotlin {
    jvmToolchain(21)
    compilerOptions { allWarningsAsErrors.set(true) }
    android {
        namespace = "io.github.kotlinmania.strumconsumer"
        compileSdk = 34
        minSdk = 24
        withHostTestBuilder { sourceSetTreeName = "test" }
    }
    sourceSets {
        commonMain.dependencies { implementation("io.github.kotlinmania:strum-kotlin:$producerVersion") }
        commonTest.dependencies { implementation(kotlin("test")) }
    }
}
tasks.withType<org.gradle.api.tasks.testing.Test>().configureEach { outputs.upToDateWhen { false } }
kotlin.targets.named("android") {
    compilations.named("main") {
        val compileDependencies = configurations.named(compileDependencyConfigurationName)
        val runtimeDependencies = configurations.named(compileDependencyConfigurationName.replace("CompileClasspath", "RuntimeClasspath"))
        tasks.register("captureSelectedPublications") {
            doLast {
                val report = mutableMapOf<String, Any>()
                for ((name, provider) in mapOf("compile" to compileDependencies, "runtime" to runtimeDependencies)) {
                    val configuration = provider.get()
                    val root = configuration.incoming.resolutionResult.rootComponent.get().dependencies
                        .filterIsInstance<org.gradle.api.artifacts.result.ResolvedDependencyResult>()
                        .single { it.selected.moduleVersion?.name == "strum-kotlin" }
                    val selected = root.resolvedVariant.externalVariant.orElse(root.resolvedVariant)
                    val owner = selected.owner as org.gradle.api.artifacts.component.ModuleComponentIdentifier
                    check(owner.group == "io.github.kotlinmania" && owner.module == "strum-kotlin-android" && owner.version == producerVersion)
                    val components = configuration.incoming.resolutionResult.allComponents.map { it.id }
                        .filterIsInstance<org.gradle.api.artifacts.component.ModuleComponentIdentifier>()
                    check(components.none { it.group == "org.jetbrains.kotlinx" })
                    check(components.filter { it.group == "io.github.kotlinmania" }.all { it.module in setOf("strum-kotlin", "strum-kotlin-android") })
                    val artifacts = configuration.incoming.artifacts.artifacts.map { artifact ->
                        mapOf("component" to artifact.id.componentIdentifier.displayName, "path" to artifact.file.absolutePath,
                            "sha256" to MessageDigest.getInstance("SHA-256").digest(artifact.file.readBytes()).joinToString("") { "%02x".format(it) })
                    }
                    report[name] = mapOf("selectedVariant" to owner.displayName, "components" to components.map { it.displayName }.sorted(), "artifacts" to artifacts)
                }
                layout.buildDirectory.file("reports/selected-publications.json").get().asFile.apply {
                    parentFile.mkdirs()
                    writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(report)) + "\n")
                }
            }
        }
    }
}
