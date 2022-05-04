package com.githukudenis.androidtesting.util

import com.githukudenis.androidtesting.util.CheckBracesUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CheckBracesUtilTest {

    @Test
    fun `left number of braces equal to right number of braces returns true`() {
        val countResult = CheckBracesUtil.checkBraces("((a + b))")
        assertThat(countResult).isTrue()
    }

    @Test
    fun `left number of braces not equal to right number of braces returns false`() {
        val countResult = CheckBracesUtil.checkBraces("(a + b))")
        assertThat(countResult).isFalse()
    }
}
