plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-M6"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.ai:spring-ai-bedrock-converse-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-mcp-client-spring-boot-starter")
}

/*
dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:1.0.0-M6")
    }
}
*/

kotlin {
    jvmToolchain(21)
}

