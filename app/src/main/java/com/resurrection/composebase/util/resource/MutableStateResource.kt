package com.resurrection.composebase.util.resource

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun <T> mutableStateResourceOf(
    data: T? = null,
    message: String? = null,
    loading: Boolean? = false
): MutableState<StateResource<T>> {
    val resource = StateResource<T>()
    resource.data.value = data
    resource.message.value = message
    resource.loading.value = loading
    return mutableStateOf(resource)
}

fun <T> MutableState<StateResource<T>>.postSuccess(data: T?) {
    val resource = this.value
    resource.data.value = data
    resource.status.value = Status.SUCCESS
    this.value = resource
}

fun <T> MutableState<StateResource<T>>.postLoading(loading: Boolean?) {
    val resource = this.value
    resource.loading.value = loading
    resource.status.value = Status.LOADING
    this.value = resource
}

fun <T> MutableState<StateResource<T>>.postError(message: String?) {
    val resource = this.value
    resource.message.value = message
    resource.status.value = Status.ERROR
    this.value = resource
}

fun <T> MutableState<StateResource<T>>.postResource(
    status: Status,
    data: T? = null,
    message: String? = null,
    loading: Boolean = false
) {
    val resource = this.value
    resource.data.value = data
    resource.loading.value = loading
    resource.message.value = message
    resource.status.value = status
    this.value = resource
}
/*
val <T> MutableState<StateResource<T>>.data get() = this.value.data.value
val <T> MutableState<StateResource<T>>.message get() = this.value.message.value
val <T> MutableState<StateResource<T>>.loading get() = this.value.loading.value*/
