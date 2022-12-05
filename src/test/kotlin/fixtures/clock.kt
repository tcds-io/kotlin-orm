package fixtures

import io.mockk.every
import io.mockk.mockkStatic
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

fun freezeClockAt(at: String) {
    mockkStatic(Clock::class)
    every { Clock.systemDefaultZone() } returns Clock.fixed(Instant.parse(at), ZoneOffset.UTC)
}
