package com.githukudenis.androidtesting

object FibonacciUtil {

    /**
     * ...fib(0) = 0
     * ...fib(1) = 1
     * ...fib(n) = fib(n-1) + fib(n-2)
     */

    fun fib(n: Int): Long {
        if (n == 0 || n == 1) {
            return n.toLong()
        }
        var a = 0L
        var b = 1L
        var c = 1L

        (1 until n).forEach { _ ->
            c = a + b
            a = b
            b = c
        }
        return c
    }
}