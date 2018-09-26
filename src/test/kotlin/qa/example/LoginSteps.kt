package qa.example

import com.typesafe.config.Config
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.qameta.allure.Step
import org.openqa.selenium.WebDriver
import org.slf4j.Logger

import qa.example.actors.Role
import qa.example.extensions.WithConfig
import qa.example.extensions.WithLog
import qa.example.extensions.WithScreenshots
import qa.example.extensions.WithWebDriver

import qa.example.pages.login.LandingPage
import qa.example.pages.login.LoginPage


class LoginSteps: WithLog, WithConfig, WithWebDriver, WithScreenshots {

    override val log: Logger = logger()
    override val config: Config = config()
    override val driver: WebDriver = driver()

    val loginPage = LoginPage(driver)
    val landingPage = LandingPage(driver)

    @Before
    fun beforeScenario() {
        log.info(">>>>>>>>>>>>>>>>>>>>>")
    }

    @After
    fun afterScenario() {
        driver.quit()
        log.info("<<<<<<<<<<<<<<<<<<<<<")
    }

    @Step
    @Given("^(\\w+) navigates Login page$")
    fun userNavigateLoginPage(name: String) {

        val role:Role = Role.valueOf(name)
        log.debug("role: {} email:{} pass:{}",role.name, role.creds.email, role.creds.pass)

        landingPage.open()

    }

    @Step
    @When("^(\\w+) provides correct email, password and clicks 'log in' button$")
    fun logsIn(name: String) {
        val role:Role = Role.valueOf(name)
        log.debug("role: {} email:{} pass:{}",role.name, role.creds.email, role.creds.pass)
        loginPage.login(role.creds)
    }

    @Step
    @Then("^(\\w+) page is opened$")
    fun pageIsOpened(name: String) {
        log.debug("name: {} ",name)
        landingPage.waitForPresent()
    }

}
