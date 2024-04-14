package fixtures

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.tcds.orm.extension.toDate
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

val frozenClockAt: Date = LocalDateTime.of(2022, 12, 18, 5, 48, 52).toDate()

fun freezeClock(at: String = "2022-12-18T05:48:52Z", block: () -> Unit) {
    mockkStatic(Clock::class)
    every { Clock.systemDefaultZone() } returns Clock.fixed(Instant.parse(at), ZoneOffset.UTC)
    block()
    unmockkStatic(Clock::class)
}
