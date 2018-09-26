package qa.example.actors

import com.typesafe.config.ConfigBeanFactory
import qa.example.configs.Credentials
import qa.example.extensions.WithConfig

enum class Role(role:String) : WithConfig {
    ADMIN("Admin"),
    TENANT_ADMIN("TenantAdmin"),
    RESTRICTED_USER("User");

    override val config = config()

    val creds:Credentials = ConfigBeanFactory.create(config.getConfig(role), Credentials::class.java)

}