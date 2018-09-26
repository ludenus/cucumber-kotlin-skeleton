package qa.example.drivers

import qa.example.configs.WebDriverConfig
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

import java.net.URL

internal class WebDriverProviderFirefox(wdConfig: WebDriverConfig) : WebDriverProvider(wdConfig) {

    override val webDriver: WebDriver
        get() {
            val webDriver: WebDriver
            if (wdConfig.useSeleniumGrid) {
                log.info("using seleniumGridUrl:" + wdConfig.seleniumGridUrl)
                webDriver = RemoteWebDriver(URL(wdConfig.seleniumGridUrl), desiredCapabilities)
            } else {
                val firefoxDriverPath = resolveDriverPath(wdConfig.browser)
                setExecPermissions(firefoxDriverPath)
                System.setProperty("webdriver.gecko.driver", firefoxDriverPath)
                webDriver = FirefoxDriver(desiredCapabilities)
            }
            setWebDriverTimeouts(webDriver)
            setWebDriverWindowSize(webDriver)

            return webDriver
        }

    override val driverPath: String
        get() = wdConfig.geckodriverPath

    val desiredCapabilities: DesiredCapabilities
        get() {
            val capabilities = DesiredCapabilities.firefox()
            capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile)
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions)
            return capabilities
        }

    //addArguments("acceptInsecureCerts")
    val firefoxOptions: FirefoxOptions
        get() = FirefoxOptions()

    val firefoxProfile: FirefoxProfile
        get() {
            val profile = FirefoxProfile()
            profile.setPreference("app.update.enabled", false)
            profile.setAcceptUntrustedCertificates(true)
            return profile
        }

}