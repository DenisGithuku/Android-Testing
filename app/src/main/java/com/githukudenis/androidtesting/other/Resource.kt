package com.githukudenis.androidtesting.other

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T?, message: String): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
