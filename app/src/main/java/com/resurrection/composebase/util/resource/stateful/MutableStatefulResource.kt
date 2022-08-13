package com.resurrection.composebase.util.resource.stateful

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun <T> mutableStatefulResourceOf(
    status: com.resurrection.composebase.state.Status,
    data: T? = null,
    throwable: Throwable? = null,
): MutableState<StatefulResource<T>> {
    val resource = StatefulResource<T>()
    resource.status.value = status
    resource.data.value = data
    resource.throwable.value = throwable
    return mutableStateOf(resource)
}

fun <T> MutableState<StatefulResource<T>>.postSuccess(data: T?) {
    val resource = this.value
    resource.data.value = data
    resource.status.value = com.resurrection.composebase.state.Status.SUCCESS
    this.value = resource
}

fun <T> MutableState<StatefulResource<T>>.postLoading() {
    val resource = this.value
    resource.status.value = com.resurrection.composebase.state.Status.LOADING
    this.value = resource
}

fun <T> MutableState<StatefulResource<T>>.postError(throwable: Throwable?) {
    val resource = this.value
    resource.throwable.value = throwable
    resource.status.value = com.resurrection.composebase.state.Status.ERROR
    this.value = resource
}
/*
val <T> MutableState<StateResource<T>>.data get() = this.value.data.value
val <T> MutableState<StateResource<T>>.message get() = this.value.message.value
val <T> MutableState<StateResource<T>>.loading get() = this.value.loading.value*/
