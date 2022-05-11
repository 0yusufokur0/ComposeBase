package com.atilsamancioglu.cryptocrazycompose.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    INVALID
}
class TestResource<T>(){
    var data = mutableStateOf<T?>(null)
    var message = mutableStateOf<String?>("")
    var loading = mutableStateOf<Boolean?>(false)
    var status = mutableStateOf(Status.LOADING)
}


fun <T> mutableStateResourceOf(data: T? = null, message: String? = null, loading: Boolean? = false): MutableState<TestResource<T>> {
    val resource = TestResource<T>()
    resource.data.value = data
    resource.message.value = message
    resource.loading.value = loading
    return mutableStateOf(resource)
}


fun <T> MutableState<TestResource<T>>.postSuccess(data:T?){
    val resource = this.value
    resource.data.value = data
    resource.loading.value = false
    resource.status.value =Status.SUCCESS
    this.value = resource
}

fun <T> MutableState<TestResource<T>>.postLoading(loading: Boolean?){
    val resource = this.value
    resource.loading.value = loading
    resource.status.value= Status.LOADING
    this.value = resource
}

fun <T> MutableState<TestResource<T>>.postError(message: String?){
    val resource = this.value
    resource.message.value = message
    resource.loading.value = false
    resource.status.value = Status.ERROR
    this.value = resource
}

fun<T> MutableState<TestResource<T>>.postResource(status:Status ,data: T? = null, message: String? = null, loading: Boolean = false){
    val resource = this.value
    resource.data.value = data
    resource.loading.value = loading
    resource.message.value = message
    this.value = resource
}

val <T> MutableState<TestResource<T>>.data get() = this.value.data.value
val <T> MutableState<TestResource<T>>.message get() = this.value.message.value
val <T> MutableState<TestResource<T>>.loading get() = this.value.loading.value
