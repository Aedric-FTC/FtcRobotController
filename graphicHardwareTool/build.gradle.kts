plugins {
    id("java-library")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {
    implementation(project(":graphicHardware"))
}
tasks.jar {
    manifest { attributes["Main-Class"] = "org."}
}