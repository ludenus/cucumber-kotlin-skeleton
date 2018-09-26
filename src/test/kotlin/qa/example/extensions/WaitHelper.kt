package qa.example.extensions

import com.github.rholder.retry.*
import org.slf4j.Logger
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


class WaitHelper<V> : WithLog {

    override val log: Logger = logger()

    val multiplier: Long = 500

    companion object {

        fun <V> retry(timeoutSeconds: Long, callable: Callable<V>): V {
            val waitHelper = WaitHelper<V>()
            return waitHelper.BuildRetryer(timeoutSeconds).call(callable)
        }
    }

    fun BuildRetryer(timeoutSeconds: Long): Retryer<V> {

        return RetryerBuilder.newBuilder<V>()
                .retryIfExceptionOfType(Throwable::class.java)
                .withWaitStrategy(WaitStrategies.fibonacciWait(multiplier, timeoutSeconds, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterDelay(timeoutSeconds, TimeUnit.SECONDS))
                .withRetryListener(getRetryListener())
                .build()
    }

    fun getRetryListener(): RetryListener {

        val retryListener = object : RetryListener {
            override fun <V> onRetry(attempt: Attempt<V>) {
                val number = attempt.attemptNumber

                val res = when {
                    attempt.hasResult() -> attempt.result
                    attempt.hasException() -> attempt.exceptionCause
                    else -> throw IllegalStateException("attempt $attempt has neither result nor exception, how come?! ")
                }

                log.debug("attempt #$number : $res")
            }
        }

        return retryListener
    }

}