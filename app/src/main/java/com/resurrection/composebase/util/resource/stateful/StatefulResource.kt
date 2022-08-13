package com.resurrection.composebase.util.resource.stateful

import androidx.compose.runtime.mutableStateOf

class StatefulResource<T>(){
    var data = mutableStateOf<T?>(null)
    var throwable = mutableStateOf<Throwable?>(Throwable(""))
    var status = mutableStateOf(com.resurrection.composebase.state.Status.LOADING)
}