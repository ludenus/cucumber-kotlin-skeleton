package qa.example.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface WithLog {
    val log: Logger

    fun logger(): Logger {
        return LoggerFactory.getLogger(this.javaClass.name)
    }
}