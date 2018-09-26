package qa.example.extensions

import qa.example.drivers.WebDriverProvider
import org.openqa.selenium.WebDriver


interface WithWebDriver : WithConfig {

    val driver: WebDriver

    fun driver(): WebDriver {
        return WebDriverProvider.getWebDriverInstance(config)
    }
}