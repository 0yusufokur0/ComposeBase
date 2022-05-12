package com.resurrection.composebase.util.resource.stateful

import androidx.compose.runtime.mutableStateOf
import com.resurrection.composebase.util.resource.Status

class StatefulResource<T>(){
    var data = mutableStateOf<T?>(null)
    var throwable = mutableStateOf<Throwable?>(Throwable(""))
    var status = mutableStateOf(Status.LOADING)
}