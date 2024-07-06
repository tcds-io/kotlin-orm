package io.tcds.orm.param

import fixtures.Status
import io.tcds.orm.extension.param
import io.tcds.orm.extension.toInstant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ParamExtensionTest {
    @Test
    fun `boolean param extension`() {
        val value = false

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(BooleanParam::class.java, param)
    }

    @Test
    fun `double param extension`() {
        val value = 100.99

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(DoubleParam::class.java, param)
    }

    @Test
    fun `enum param extension`() {
        val value = Status.ACTIVE

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(EnumParam::class.java, param)
    }

    @Test
    fun `float param extension`() {
        val value = 100.99.toFloat()

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(FloatParam::class.java, param)
    }

    @Test
    fun `instant param extension`() {
        val value = LocalDateTime.of(2021, 1, 1, 1, 1, 1).toInstant()

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(InstantParam::class.java, param)
    }

    @Test
    fun `integer param extension`() {
        val value = 10

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(IntegerParam::class.java, param)
    }

    @Test
    fun `long param extension`() {
        val value = 10L

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(LongParam::class.java, param)
    }

    @Test
    fun `string param extension`() {
        val value = "Arthur Dent"

        val param = param(value)

        Assertions.assertEquals(value, param.value)
        Assertions.assertEquals("", param.name)
        Assertions.assertInstanceOf(StringParam::class.java, param)
    }
}
