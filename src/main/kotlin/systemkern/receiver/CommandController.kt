package systemkern.receiver

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/execute-command")
internal class CommandReceiverController(
    private val service: CommandService
) {
    val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("{id}")
    fun get(@PathVariable id: String): ReturnDTO =
        ReturnDTO(
            output = service.runCommand(id)
        )

    @PostMapping("{id}")
    fun post(@PathVariable id: String) =
        ReturnDTO(
            output = service.runCommand(id)
        )
}


internal data class ReturnDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val output: String
)

