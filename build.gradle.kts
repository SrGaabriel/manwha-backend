@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(vibra.plugins.kotlin.jvm)
}

subprojects {
    group = "io.github.vibraplatform"
    version = "1.0"
}

repositories {
    mavenCentral()
}