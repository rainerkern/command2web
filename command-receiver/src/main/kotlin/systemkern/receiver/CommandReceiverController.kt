package systemkern.receiver

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@RestController("/execute-command")
internal class CommandReceiverController(
    val config: CommandReceiverConfiguration
) {

    val log = LoggerFactory.getLogger(CommandReceiverController::class.java)

    @PostConstruct
    fun init() {
        if (config.commands.isEmpty())
            throw IllegalArgumentException("No commands defined")
    }

    @PostMapping
    fun runCommandPost() =
        runCommand()

    @GetMapping
    fun runCommand(): ReturnDTO {
        log.info("Command to run is: ${config.commands}")

        val buff = StringBuffer()
        config.commands.forEach { cur ->
            val proc = Runtime.getRuntime().exec(cur)

            Scanner(proc.inputStream).use {
                while (it.hasNextLine()) {
                    val str = it.nextLine()
                    buff.append("$str\n")
                    log.trace(str)
                }
            }
            proc.waitFor(20, TimeUnit.SECONDS)

            Thread.sleep(config.delay)
        }


        return ReturnDTO(output = "shell output: " + buff.toString())
    }
}

internal data class ReturnDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val output: String
)

@Configuration
@ConfigurationProperties(prefix = "command-receiver")
internal class CommandReceiverConfiguration {
    var delay: Long = 5000L
    var commands: List<String> = listOf()
}
