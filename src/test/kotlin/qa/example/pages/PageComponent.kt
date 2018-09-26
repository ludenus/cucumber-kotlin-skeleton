package qa.example.pages

import qa.example.extensions.WithConfig
import qa.example.extensions.WithLog
import com.typesafe.config.Config
import org.openqa.selenium.WebDriver
import org.slf4j.Logger

abstract class PageComponent(val driver: WebDriver) : WithLog, WithConfig {

    override val log: Logger = logger()
    override val config: Config = config()

    abstract fun waitForPresent()

}