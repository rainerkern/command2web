package systemkern.entity

import org.javamoney.moneta.Money
import java.time.LocalDateTime
import javax.money.MonetaryAmount

internal data class EchoDTO(
    val id: Int,
    val value: String,
    val timestamp: LocalDateTime? = LocalDateTime.now(),
    val money: MonetaryAmount = Money.of(0, "EUR")
)
