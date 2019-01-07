package systemkern.entity

import java.time.Duration

internal class CommandEntity {
    var script: List<String> = listOf()
    var delay: Duration = Duration.ZERO
}
