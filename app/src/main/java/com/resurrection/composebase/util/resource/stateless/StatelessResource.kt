package com.resurrection.composebase.util.resource.stateless

import androidx.compose.runtime.mutableStateOf
import com.resurrection.composebase.util.resource.Status

class StatelessResource<T> {
        var data :T? = null
        var throwable:Throwable? = null
        var status = mutableStateOf(Status.LOADING)
}