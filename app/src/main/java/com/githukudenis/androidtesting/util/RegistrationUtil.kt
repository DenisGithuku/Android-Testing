package com.githukudenis.androidtesting.util

object RegistrationUtil {

    private val existing_users = listOf("Peter", "Carl")

    /**
     * The input is not valid if...
     * ....username/password is empty
     * ....username is already taken
     * ....confirmed password is not the same as the real password
     * ....password contains less than 2 digits
     */

    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false
        }
        if (password != confirmPassword) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }
        if (username in existing_users) {
            return false
        }
        return true
    }
}