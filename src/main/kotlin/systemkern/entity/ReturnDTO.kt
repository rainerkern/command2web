package systemkern.entity

import java.time.LocalDateTime

internal data class ReturnDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val output: String
)