package qa.example.configs

class WebDriverConfig {

    var browser: Browser = Browser.CHROME
    var useSeleniumGrid: Boolean = false
    var seleniumGridUrl: String = ""
    var chromedriverPath: String = ""
    var geckodriverPath: String = ""
    var windowWidth: Int = 0
    var windowHeight: Int = 0
    var scriptTimeoutSeconds: Long = 0
    var pageLoadTimeoutSeconds: Long = 0
    var implicitWaitTimeoutSeconds: Long = 0

    enum class Browser {
        FIREFOX, CHROME;

        fun getResourcePath(osName: String): String {

            val os = when {
                osName.startsWith("Linux") -> "linux64"
                osName.startsWith("Mac") -> "mac64"
                else -> throw IllegalArgumentException("OS not supported: " + osName)
            }

            when (this) {
                CHROME -> return "chromedriver/$os/chromedriver"
                FIREFOX -> return "geckodriver/$os/geckodriver"
                else -> throw IllegalStateException("browser type not recognized")
            }
        }

    }
}