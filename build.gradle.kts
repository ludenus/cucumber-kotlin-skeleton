import de.undercouch.gradle.tasks.download.Download
import de.undercouch.gradle.tasks.download.Verify
import org.gradle.internal.impldep.org.apache.tools.zip.UnsupportedZipFeatureException
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.js.dce.InputResource


plugins {
    kotlin("jvm") version "1.2.60"
    id("io.qameta.allure") version "2.5"
    id("de.undercouch.download") version "3.4.3"
}


allure {
    version = "2.5.0"
    autoconfigure = true
    aspectjweaver = true
    resultsDir = file("allure-results")
    reportDir = file("allure-report")
}


val test by tasks.getting(Test::class) {
    val testNGOptions = closureOf<TestNGOptions> {
        suites("src/test/resources/testng.xml")
    }

    useTestNG(testNGOptions)
    testLogging.showStandardStreams = true

    dependsOn("prepareWebDrivers")
}


val chromedriverVersion = project.findProperty("chromedriverVersion") ?: "2.36"
val geckodriverVersion = project.findProperty("geckodriverVersion") ?: "v0.18.0"


task<Download>("wget-chromedriver-mac64-zip") {
    src("https://chromedriver.storage.googleapis.com/${chromedriverVersion}/chromedriver_mac64.zip")
    dest(File("src/test/resources"))
    overwrite(false)
}
task<Verify>("verify-chromedriver-mac64-zip") {
    src(File("src/test/resources/chromedriver_mac64.zip"))
    algorithm("MD5")
    checksum("312cd778e385a255c60caed5ffbaf6e5")
    dependsOn("wget-chromedriver-mac64-zip")
}
task<Copy>("unzip-chromedriver-mac64"){
    from(zipTree(File("src/test/resources/chromedriver_mac64.zip")))
    into(File("src/test/resources/chromedriver/macos"))
    dependsOn("verify-chromedriver-mac64-zip")
}


task<Download>("wget-chromedriver-linux64-zip") {
    src("https://chromedriver.storage.googleapis.com/${chromedriverVersion}/chromedriver_linux64.zip")
    dest(File("src/test/resources"))
    overwrite(false)
}
task<Verify>("verify-chromedriver-linux64-zip") {
    src(File("src/test/resources/chromedriver_linux64.zip"))
    algorithm("MD5")
    checksum("24d2004a0b6c9fb4fcd74d1966b0ca6e")
    dependsOn("wget-chromedriver-linux64-zip")
}
task<Copy>("unzip-chromedriver-linux64"){
    from(zipTree(File("src/test/resources/chromedriver_linux64.zip")))
    into(File("src/test/resources/chromedriver/linux64"))
    dependsOn("verify-chromedriver-linux64-zip")
}


task<Download>("wget-geckodriver-mac64-zip") {
    src("https://github.com/mozilla/geckodriver/releases/download/${geckodriverVersion}/geckodriver-${geckodriverVersion}-macos.tar.gz")
    dest(File("src/test/resources"))
    overwrite(false)
}
task<Verify>("verify-geckodriver-mac64-zip") {
    src(File("src/test/resources/geckodriver-${geckodriverVersion}-macos.tar.gz"))
    algorithm("MD5")
    checksum("79cf3050cc942cdff6edbc7605d05ef2")
    dependsOn("wget-geckodriver-mac64-zip")
}
task<Copy>("unzip-geckodriver-mac64"){
    from(tarTree(File("src/test/resources/geckodriver-${geckodriverVersion}-macos.tar.gz")))
    into(File("src/test/resources/geckodriver/macos"))
    dependsOn("verify-geckodriver-mac64-zip")
}

task<Download>("wget-geckodriver-linux64-zip") {
    src("https://github.com/mozilla/geckodriver/releases/download/${geckodriverVersion}/geckodriver-${geckodriverVersion}-linux64.tar.gz")
    dest(File("src/test/resources"))
    overwrite(false)
}
task<Verify>("verify-geckodriver-linux64-zip") {
    src(File("src/test/resources/geckodriver-${geckodriverVersion}-linux64.tar.gz"))
    algorithm("MD5")
    checksum("4ccb56fb3700005c9f9188f84152f21a")
    dependsOn("wget-geckodriver-linux64-zip")
}
task<Copy>("unzip-geckodriver-linux64"){
    from(tarTree(File("src/test/resources/geckodriver-${geckodriverVersion}-linux64.tar.gz")))
    into(File("src/test/resources/geckodriver/linux64"))
    dependsOn("verify-geckodriver-linux64-zip")
}


task("prepareWebDrivers") {
    dependsOn("unzip-chromedriver-mac64")
    dependsOn("unzip-chromedriver-linux64")
    dependsOn("unzip-geckodriver-mac64")
    dependsOn("unzip-geckodriver-linux64")
}


dependencies {

    compileClasspath("io.qameta.allure:allure-gradle:2.5")

    implementation("org.slf4j:slf4j-api:1.7.21")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("com.typesafe:config:1.3.3")

    implementation("io.cucumber:cucumber-java:4.0.0")
    implementation("io.cucumber:cucumber-testng:4.0.0")

    implementation("org.testng:testng:6.13.1")

    // http://www.unitils.org/tutorial-reflectionassert.html
    implementation("org.unitils:unitils-core:3.4.6")

    implementation("com.github.rholder:guava-retrying:2.0.0")

    implementation("org.seleniumhq.selenium:selenium-java:3.11.0")
    implementation("org.seleniumhq.selenium:selenium-support:3.11.0")

    implementation("ru.yandex.qatools.ashot:ashot:1.5.4")

    implementation(kotlin("stdlib", "1.2.60"))

    implementation("org.jetbrains.kotlin:kotlin-test:1.2.60")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.2.60")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.5")
}


repositories {
    mavenCentral()
    jcenter()
}


kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

