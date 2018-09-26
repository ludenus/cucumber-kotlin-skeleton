package qa.example

import cucumber.api.CucumberOptions
import cucumber.api.testng.AbstractTestNGCucumberTests
import org.testng.annotations.Test

@Test
@CucumberOptions(
        plugin = ["pretty"]
)
class CucumberRunner : AbstractTestNGCucumberTests()
