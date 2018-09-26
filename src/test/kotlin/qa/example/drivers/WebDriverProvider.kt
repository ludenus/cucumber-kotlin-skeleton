package qa.example.drivers

import qa.example.configs.WebDriverConfig
import qa.example.configs.WebDriverConfig.Browser.CHROME
import qa.example.configs.WebDriverConfig.Browser.FIREFOX
import com.typesafe.config.Config
import com.typesafe.config.ConfigBeanFactory
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.TimeUnit

abstract class WebDriverProvider(protected val wdConfig: WebDriverConfig) {

    protected val log = LoggerFactory.getLogger(this.javaClass)
    abstract val webDriver: WebDriver
    protected abstract val driverPath: String

    companion object {
        fun getWebDriverInstance(config: Config): WebDriver {
            val wdConfig = ConfigBeanFactory.create(config.getConfig("webdriver"), WebDriverConfig::class.java)
            val browser = wdConfig.browser
            return when (browser) {
                CHROME -> WebDriverProviderChrome(wdConfig).webDriver
                FIREFOX -> WebDriverProviderFirefox(wdConfig).webDriver
            }
        }
    }

    protected fun setWebDriverTimeouts(driver: WebDriver) {
        driver.manage().timeouts().setScriptTimeout(wdConfig.scriptTimeoutSeconds, TimeUnit.SECONDS)
        driver.manage().timeouts().pageLoadTimeout(wdConfig.pageLoadTimeoutSeconds, TimeUnit.SECONDS)
        driver.manage().timeouts().implicitlyWait(wdConfig.implicitWaitTimeoutSeconds, TimeUnit.SECONDS)
    }

    protected fun setWebDriverWindowSize(driver: WebDriver) {
        val width = wdConfig.windowWidth
        val height = wdConfig.windowHeight
        driver.manage().window().position = Point(0, 0)
        driver.manage().window().size = Dimension(width, height)
    }

    protected fun resolveDriverPath(browser: WebDriverConfig.Browser): String {
        val osName = System.getProperty("os.name")
        val path = driverPath
        return if (driverPath == "AUTO") {
            val resource = browser.getResourcePath(osName)
            javaClass.classLoader.getResource(resource).file
        } else {
            path
        }
    }

    protected fun setExecPermissions(filename: String) {
        log.info("setting exec permissions on " + filename)
        // workaround maven bug https://issues.apache.org/jira/browse/MRESOURCES-132

        val file = File(filename)
        if (!file.exists()) {
            throw FileNotFoundException(file.canonicalPath)
        }

        file.setExecutable(true)
        if (!file.canExecute()) {
            throw IllegalStateException("file is not executable: " + file.canonicalPath)
        }
    }

}