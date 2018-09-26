package qa.example.extensions

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

interface WithConfig {
    val config: Config

    fun config(): Config {
        return ConfigFactory.load()
    }
}