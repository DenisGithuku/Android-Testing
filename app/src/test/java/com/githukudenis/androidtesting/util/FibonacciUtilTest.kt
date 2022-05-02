package com.githukudenis.androidtesting.util


import com.githukudenis.androidtesting.util.FibonacciUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FibonacciUtilTest {

    @Test
    fun `n equals 0 returns 0`() {
        val result = FibonacciUtil.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `n equals 1 returns 1`() {
        val result = FibonacciUtil.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `n equals 9 returns 34`() {
        val result = FibonacciUtil.fib(9)
        assertThat(result).isEqualTo(34)
    }
}
