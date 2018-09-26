package qa.example.pages.login

import qa.example.pages.NavigationBar
import qa.example.pages.AbstractPage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

class LandingPage(driver: WebDriver): AbstractPage(driver) {

    val navBar = NavigationBar(driver)

    init{
        PageFactory.initElements(driver, this)
    }

    override fun pageUrl()  = "${baseUrl()}/tenants"

    override fun waitForPresent() = navBar.waitForPresent()

    fun logout() = navBar.logout()

}