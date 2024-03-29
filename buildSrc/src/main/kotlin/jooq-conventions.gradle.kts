plugins {
    `java-library`
    id("org.jooq.jooq-codegen-gradle")
    id("org.liquibase.gradle")
    id("com.diffplug.spotless")
}

ext {
    set("jdbcUsername", "jooq_demo_admin") // overwritten per environment
    set("jdbcPassword", "jooq_demo_admin") // overwritten per environment
    set("jdbcUrl", "jdbc:postgresql://localhost:5432/jooq_demo")
}

ext["jooqVersion"] = "3.19.4"
ext["postgresqlVersion"] = "42.7.1"


java {
    sourceCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceSets.getByName("main").java.srcDir("build/generated-sources/jooq/demo")
    sourceSets.getByName("main").java.srcDir("build/generated-sources/jooq/staging")
    sourceSets.getByName("main").java.srcDir("build/generated-sources/jooq/extensions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq:${property("jooqVersion")}")
    implementation("org.jooq:jooq-meta:${property("jooqVersion")}")
    implementation("org.jooq:jooq-postgres-extensions:${property("jooqVersion")}")
    implementation("org.jooq:jool:0.9.15")

    jooqCodegen("org.postgresql:postgresql:${property("postgresqlVersion")}")

    liquibaseRuntime("org.liquibase:liquibase-core:4.26.0")
    liquibaseRuntime("org.postgresql:postgresql:${property("postgresqlVersion")}")
    liquibaseRuntime("info.picocli:picocli:4.7.3")

}

spotless {
    encoding("UTF-8")
    java {
        toggleOffOn("formatter:off", "formatter:on")  // same as intellij
        targetExclude("build/generated/**", "build/generated-sources/**")
        palantirJavaFormat()
    }
    kotlin {
        // by default the target is every ".kt" and ".kts` file in the java sourcesets
        ktfmt()
    }
}
