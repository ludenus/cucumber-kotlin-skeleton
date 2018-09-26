package qa.example.pages


import qa.example.extensions.WithExtensions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import io.qameta.allure.Step

class NavigationBar(driver: WebDriver) : PageComponent(driver), WithExtensions {


    @FindBy(css = ".navbar")
    lateinit var navBar: WebElement


    @FindBy(css = ".navbar  .user-name")
    lateinit var navUser: WebElement


    @FindBy(css = ".navbar  .logout")
    lateinit var logoutItem: WebElement


    init {
        PageFactory.initElements(driver, this)
    }

    override fun waitForPresent() {
        navBar.waitForPresent()
        navUser.waitForPresent()
    }

    @Step
    fun logout() {
        navBar.waitForPresent()
        navUser.waitForPresent()
        logoutItem.waitForPresent().click()
        navBar.waitForAbsent()
    }

}
