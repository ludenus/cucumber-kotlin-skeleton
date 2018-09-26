package qa.example.extensions

import java.util.concurrent.Callable

interface WithRetry : WithLog, WithConfig {

    fun <V> retry(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds"), block: () -> V): V {
        return WaitHelper.Companion.retry(timeoutSeconds, Callable<V> {
            block()
        })
    }

    fun <V> waitForResult(timeoutSeconds: Long = config.getLong("defaultWaitTimeoutSeconds"), expected: V, block: () -> V): V {
        return WaitHelper.Companion.retry(timeoutSeconds, Callable {
            val result = block()
            assert(expected == result) {"nay, $expected != $result"}
            result
        })
    }
}