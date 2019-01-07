package systemkern.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import systemkern.config.CommandReceiverConfiguration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Service
internal class CommandService(
    val config: CommandReceiverConfiguration
) {
    val log = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun init() {
        if (config.commands.isEmpty())
            throw IllegalArgumentException("No commands defined, please check your configuration.")

        log.debug("${config.commands.size} commands defined.")
        config.commands.entries.forEach {
            log.debug("Key: ${it.key} Delay: ${it.value.delay}: Command: ${it.value.script}")
        }
    }

    fun runCommand(id: String): String {
        val cur = config.commands[id]
            ?: throw IllegalArgumentException("No script with identifier $id registered.")
        log.info("script to run is \"$id\": ${cur.script.joinToString()}")


        val buff = StringBuffer()
        cur.script.forEach {
            val proc = Runtime.getRuntime().exec(it)
            Scanner(proc.inputStream).use {
                while (it.hasNextLine()) {
                    val str = it.nextLine()
                    buff.append("$str\n")
                    log.trace("stdout: $str")
                }
            }
            proc.waitFor(config.waitForPeriod.seconds, TimeUnit.SECONDS)

            Thread.sleep(cur.delay.toMillis())
        }
        return buff.toString()
    }
}
