package qa.example.support

import qa.example.support.TestRetryAnalyzer
import org.testng.IAnnotationTransformer
import org.testng.annotations.ITestAnnotation
import java.lang.reflect.Constructor
import java.lang.reflect.Method

class AnnotationTransformer : IAnnotationTransformer {
    override fun transform(annotation: ITestAnnotation, testClass: Class<*>?, testConstructor: Constructor<*>?, testMethod: Method?) {

        if (null == annotation.retryAnalyzer) {
            annotation.setRetryAnalyzer(TestRetryAnalyzer::class.java)
        }
    }
}