package qa.example.extensions

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import io.qameta.allure.Step
import kotlin.test.fail


interface WithExtensions : WithLog, WithConfig, WithRetry, WithWebDriver {

    fun WebElement.isNotDisplayed(): Boolean {
        return try {
            !this.isDisplayed
        } catch (e: NoSuchElementException) {
            true
        }
    }

    fun WebElement.waitForPresent(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds")): WebElement {
        return retry(timeoutSeconds) {
            this.isDisplayed || fail("still waiting for element to be displayed: $this")
            this
        }
    }

    fun WebElement.waitForAbsent(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds")) {
        retry(timeoutSeconds) {
            this.isNotDisplayed() || fail("still waiting for element to disappear: $this")
        }
    }

    @Step("Wait for document ready within {1} seconds...")
    fun waitForDocumentReady(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds")) {
        WebDriverWait(driver, timeoutSeconds).until { wd ->
            "complete" == (wd as JavascriptExecutor).executeScript("return document.readyState")
        }
    }

    @Step("Wait for ajax to finish within {1} seconds...")
    fun waitForAjaxToFinish(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds")) {
        retry(timeoutSeconds) {
            val js = driver as JavascriptExecutor
            assert(
                    js.executeScript("return !!jQuery && jQuery.active == 0") as Boolean
            ) { "ajax is still in progress" }
        }
    }


}