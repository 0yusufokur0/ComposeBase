package com.resurrection.composebase.util.resource.stateful

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.resurrection.composebase.util.resource.Status

fun <T> mutableStateResourceOf(
    data: T? = null,
    message: String? = null,
    loading: Boolean? = false
): MutableState<StatefulResource<T>> {
    val resource = StatefulResource<T>()
    resource.data.value = data
    resource.message.value = message
    return mutableStateOf(resource)
}

fun <T> MutableState<StatefulResource<T>>.postSuccess(data: T?) {
    val resource = this.value
    resource.data.value = data
    resource.status.value = Status.SUCCESS
    this.value = resource
}

fun <T> MutableState<StatefulResource<T>>.postLoading() {
    val resource = this.value
    resource.status.value = Status.LOADING
    this.value = resource
}

fun <T> MutableState<StatefulResource<T>>.postError(message: String?) {
    val resource = this.value
    resource.message.value = message
    resource.status.value = Status.ERROR
    this.value = resource
}

fun <T> MutableState<StatefulResource<T>>.postResource(
    status: Status,
    data: T? = null,
    message: String? = null,
) {
    val resource = this.value
    resource.data.value = data
    resource.message.value = message
    resource.status.value = status
    this.value = resource
}
/*
val <T> MutableState<StateResource<T>>.data get() = this.value.data.value
val <T> MutableState<StateResource<T>>.message get() = this.value.message.value
val <T> MutableState<StateResource<T>>.loading get() = this.value.loading.value*/
