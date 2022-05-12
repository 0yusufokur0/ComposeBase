package com.resurrection.composebase.util.resource.stateless

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.resurrection.composebase.util.resource.Status
import com.resurrection.composebase.util.resource.stateful.StatefulResource

fun <T> mutableStatelessResourceOf(
    status: Status,
    data: T? = null,
    throwable: Throwable? = null,
): MutableState<StatelessResource<T>> {
    val resource = StatelessResource<T>()
    resource.status.value = status
    resource.data = data
    resource.throwable = throwable
    return mutableStateOf(resource)
}

fun <T> MutableState<StatelessResource<T>>.postSuccess(data: T?) {
    val resource = this.value
    resource.data = data
    resource.status.value = Status.SUCCESS
    this.value = resource
}

fun <T> MutableState<StatelessResource<T>>.postLoading() {
    val resource = this.value
    resource.status.value = Status.LOADING
    this.value = resource
}

fun <T> MutableState<StatelessResource<T>>.postError(throwable: Throwable?) {
    val resource = this.value
    resource.throwable = throwable
    resource.status.value = Status.ERROR
    this.value = resource
}