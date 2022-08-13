package com.resurrection.composebase.state

/*sealed class Resource<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(throwable: Throwable?, data: T? = null) : Resource<T>(data, throwable)
    class Loading<T> : Resource<T>()
}*/

sealed class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {

    class Loading<T> : Resource<T>(Status.LOADING, null, null)
    class Success<T>(data: T?) : Resource<T>(Status.SUCCESS, data, null)
    class Error<T>(throwable: Throwable) : Resource<T>(Status.ERROR, null, throwable)

}