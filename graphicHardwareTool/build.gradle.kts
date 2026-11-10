plugins {
    id("java-library")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {
    implementation(project(":graphicHardware"))
    implementation("com.google.code.gson:gson:2.15.0")
}
tasks.jar {
    manifest { attributes["Main-Class"] = "org."}
}