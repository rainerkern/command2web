package systemkern.entity

import java.time.LocalDateTime

internal data class PongDTO(
    val timestamp: LocalDateTime = LocalDateTime.now()
)