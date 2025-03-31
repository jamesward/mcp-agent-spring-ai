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
    implementation("org.springframework.ai:spring-ai-bedrock-converse-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-mcp-client-spring-boot-starter")

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.amazonaws.serverless:aws-serverless-java-container-springboot3:2.1.2")
}

kotlin {
    jvmToolchain(21)
}

// exclude the tomcat jars from going into the lambda, but only on `sam build`
configurations {
    runtimeClasspath {
        if (System.getProperty("software.amazon.aws.lambdabuilders.scratch-dir") != null) {
            exclude("org.apache.tomcat.embed")
        }
    }
}
