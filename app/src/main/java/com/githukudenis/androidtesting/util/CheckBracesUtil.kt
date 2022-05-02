package com.githukudenis.androidtesting.util

object CheckBracesUtil {

    /**
     * ..checks if braces are placed correctly
     * e.g (a+ b)) returns false
     */
    fun checkBraces(string: String): Boolean {
        return string.count { it == '(' } == string.count { it == ')' }
    }
}