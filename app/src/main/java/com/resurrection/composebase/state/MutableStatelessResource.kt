package com.resurrection.composebase.state

import androidx.compose.runtime.mutableStateOf


//typealias MutableStatelessResource<T> = MutableState<StatelessResource<T>>

class MutableStatelessResource<T>{

    inner class StatelessResource<T> {
        var data :T? = null
        var throwable:Throwable? = null
        var status = mutableStateOf(Status.LOADING)
    }

    val resource = mutableStateOf(StatelessResource<T>())

    protected fun postSuccess(data: T?) {
        val tempResource = resource.value
        tempResource.data = data
        tempResource.status.value = Status.SUCCESS
        resource.value = tempResource
    }

    protected fun postLoading() {
        val tempResource = resource.value
        tempResource.status.value = Status.LOADING
        resource.value = tempResource
    }

    protected fun postError(throwable: Throwable?) {
        val tempResource = resource.value
        tempResource.throwable = throwable
        tempResource.status.value = Status.ERROR
        resource.value = tempResource
    }

}

fun <T> mutableStatelessResourceOf(
    status: Status = Status.LOADING,
    data: T? = null,
    throwable: Throwable? = null,
): MutableStatelessResource<T> {
    val mutableStatelessResource = MutableStatelessResource<T>().apply {
        resource.value.status.value = status
        resource.value.data = data
        resource.value.throwable = throwable
    }
    return mutableStatelessResource
}
