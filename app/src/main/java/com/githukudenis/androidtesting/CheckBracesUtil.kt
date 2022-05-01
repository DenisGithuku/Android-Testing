package com.githukudenis.androidtesting

object CheckBracesUtil {

    /**
     * ..checks if braces are placed correctly
     * e.g (a+ b)) returns false
     */
    fun checkBraces(string: String): Boolean {
        return string.count { it == '(' } == string.count { it == ')' }
    }
}