package qa.example.tests

import com.typesafe.config.ConfigBeanFactory
import io.qameta.allure.Stories
import qa.example.configs.Credentials
import qa.example.extensions.WithExtensions
import qa.example.extensions.WithTimer
import qa.example.pages.login.LandingPage
import qa.example.pages.login.LoginPage
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import io.qameta.allure.Feature
import io.qameta.allure.Story

@Feature("Login")
@Story("As a User I can login")
class LoginTest : AbstractTest(), WithTimer, WithExtensions {

    val creds = ConfigBeanFactory.create(config.getConfig("default.credentials"), Credentials::class.java)
    val loginPage = LoginPage(driver)
    val landingPage = LandingPage(driver)

    @Test(dataProvider = "invalidLogin")
    fun checkInvalidLogin(user: String, pass: String) {
        loginPage.open()
        loginPage.login(user, pass)
        loginPage.waitForLoginError()
    }

    @Test(dataProvider = "validLogin", dependsOnMethods = ["checkInvalidLogin"])
    fun checkValidLogin(user: String, pass: String) {
        loginPage.open()
        loginPage.login(user, pass)
        landingPage.waitForPresent()
        landingPage.logout()
    }

    @DataProvider
    fun invalidLogin() = arrayOf(
            arrayOf("nonexisting@ema.il", "*******"),
            arrayOf(creds.email, "wrong_password")
    )

    @DataProvider
    fun validLogin() = arrayOf(
            arrayOf(creds.email, creds.pass)
//            arrayOf("this-tests@should-fa.il", "password")
    )

}