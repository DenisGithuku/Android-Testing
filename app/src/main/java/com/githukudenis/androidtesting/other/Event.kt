package com.githukudenis.androidtesting.other

open class Event <out T>(private val content: T) {
    var hasBeenHandled = false
    private set


    /**
     * returns content and prevents it's use again
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }


    /**
     * returns content even if it's already been handled
     */
    fun peekContent(): T = content
}
