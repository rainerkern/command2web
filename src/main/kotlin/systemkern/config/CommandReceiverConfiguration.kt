package systemkern.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import systemkern.entity.CommandEntity
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "command-receiver")
internal open class CommandReceiverConfiguration {
    var waitForPeriod: Duration = Duration.ofSeconds(20)
    var commands: Map<String, CommandEntity> = mutableMapOf()
}
