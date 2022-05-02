package com.githukudenis.androidtesting

import com.githukudenis.androidtesting.util.RegistrationUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("", "123456", "123456")

        assertThat(result).isFalse()
    }
    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("James", "", "123456")

        assertThat(result).isFalse()
    }
    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput("Allan", "123456", "123456")

        assertThat(result).isTrue()
    }
    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("Carl", "123456", "123456")

        assertThat(result).isFalse()
    }
    @Test
    fun `password contains less than 2 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("Carl", "1", "123456")

        assertThat(result).isFalse()
    }
    @Test
    fun `confirmed password not the same as password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("Carl", "123456", "1234")

        assertThat(result).isFalse()
    }

}