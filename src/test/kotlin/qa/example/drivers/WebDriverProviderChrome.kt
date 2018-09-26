package qa.example.drivers

import qa.example.configs.WebDriverConfig
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

import java.net.URL

class WebDriverProviderChrome(wdConfig: WebDriverConfig) : WebDriverProvider(wdConfig) {

    override val webDriver: WebDriver
        get() {
            val webDriver: WebDriver = if (wdConfig.useSeleniumGrid) {
                log.info("using seleniumGridUrl:" + wdConfig.seleniumGridUrl)
                RemoteWebDriver(URL(wdConfig.seleniumGridUrl), DesiredCapabilities.chrome())
            } else {
                val chromeDriverPath = resolveDriverPath(wdConfig.browser)
                setExecPermissions(chromeDriverPath)
                System.setProperty("webdriver.chrome.driver", chromeDriverPath)
                ChromeDriver(desiredCapabilities)
            }
            setWebDriverTimeouts(webDriver)
            setWebDriverWindowSize(webDriver)
            return webDriver
        }

    override val driverPath: String
        get() = wdConfig.chromedriverPath


    val desiredCapabilities: DesiredCapabilities
        get() {
            val capabilities = DesiredCapabilities.chrome()
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
            return capabilities
        }

    //hide "Chrome is being controlled by automated tests software"
    val chromeOptions: ChromeOptions
        get() {
            val options = ChromeOptions()
            options.addArguments("disable-infobars")
            return options
        }

}