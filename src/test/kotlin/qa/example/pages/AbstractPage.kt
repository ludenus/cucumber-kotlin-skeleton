package qa.example.pages

import qa.example.extensions.WithExtensions
import qa.example.extensions.WithPageUrl
import qa.example.extensions.WithWebDriver
import org.openqa.selenium.WebDriver
import io.qameta.allure.Step

abstract class AbstractPage(driver: WebDriver) : PageComponent(driver), WithPageUrl, WithWebDriver, WithExtensions {

    override fun baseUrl(): String = config.getString("application.url")

    fun open() = open(pageUrl())

    @Step
    private fun open(url: String) {
        log.info("open page: " + pageUrl())
        driver.get(url)
    }

}