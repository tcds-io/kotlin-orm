package fixtures

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.tcds.orm.extension.toInstant
import java.time.*

val frozenClockAt: Instant = LocalDateTime.of(2022, 12, 18, 5, 48, 52).toInstant()
val frozenClockAtApril: Instant = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toInstant()

fun freezeClock(at: String = "2022-12-18T05:48:52Z", block: () -> Unit) {
    mockkStatic(Clock::class)
    every { Clock.systemDefaultZone() } returns Clock.fixed(Instant.parse(at), ZoneOffset.UTC)
    block()
    unmockkStatic(Clock::class)
}
