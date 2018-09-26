package qa.example.support

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import org.testng.IRetryAnalyzer
import org.testng.ITestResult
import java.util.concurrent.atomic.AtomicInteger

class TestRetryAnalyzer : IRetryAnalyzer {
    val log = LoggerFactory.getLogger(this.javaClass.name)
    val counter = AtomicInteger(0)
    val testRetryLimit = ConfigFactory.load().getInt("testRetryLimit")

    private val isRetryAvailable: Boolean
        get() = counter.toInt() < testRetryLimit

    override fun retry(result: ITestResult): Boolean = if (result.isSuccess) {
        false
    } else if (isRetryAvailable) {
        counter.getAndIncrement()
        log.info("rerun {} / {} for {}#{} ", counter, testRetryLimit, result.method.testClass.name, result.method.methodName)
        result.testContext.failedTests.removeResult(result)
        true
    } else {
        false
    }

}