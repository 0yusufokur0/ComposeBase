package com.resurrection.composebase.util.resource

import androidx.compose.runtime.mutableStateOf

class StateResource<T>(){
    var data = mutableStateOf<T?>(null)
    var message = mutableStateOf<String?>("")
    var loading = mutableStateOf<Boolean?>(false)
    var status = mutableStateOf(Status.LOADING)
}