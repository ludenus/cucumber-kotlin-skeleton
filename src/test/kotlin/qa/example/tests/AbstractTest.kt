package qa.example.tests

import qa.example.extensions.WithConfig
import qa.example.extensions.WithLog
import qa.example.extensions.WithScreenshots
import qa.example.extensions.WithWebDriver
import com.typesafe.config.Config
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.testng.IHookCallBack
import org.testng.IHookable
import org.testng.ITestResult
import org.testng.annotations.AfterClass


abstract class AbstractTest : IHookable, WithLog, WithConfig, WithWebDriver, WithScreenshots {

    override val log: Logger = logger()
    override val config: Config = config()
    override val driver: WebDriver = driver()

    @AfterClass
    fun afterClass() {
        driver.quit()
    }

    override fun run(callBack: IHookCallBack, testResult: ITestResult) {
        callBack.runTestMethod(testResult)
        if (null != testResult.throwable) {
            allureAttachmentForFailed(testResult.method.methodName, allureScreenshot())
            pageScreenshot(testResult.testClass.name + "_" + testResult.method.methodName)
        }
    }

}
