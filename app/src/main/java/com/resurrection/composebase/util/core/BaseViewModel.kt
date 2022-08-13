package com.resurrection.composebase.util.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resurrection.composebase.state.MutableStatelessResource
import com.resurrection.composebase.state.Resource
import com.resurrection.composebase.util.callPrivateFunc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class BaseViewModel:ViewModel() {

    protected fun <T> MutableStatelessResource<T>.postSuccess(data: T?) = callPrivateFunc("postSuccess", data)
    protected fun <T> MutableStatelessResource<T>.postLoading() = callPrivateFunc("postLoading")
    protected fun <T> MutableStatelessResource<T>.postError(throwable: Throwable?) = callPrivateFunc("postError", throwable)

    fun <T> fetchStatelessResource(
        condition: Boolean = true,
        stateless: MutableStatelessResource<T>,
        request: suspend () -> Flow<Resource<T>>,
        success: (Resource<T>) -> Unit = { stateless.postSuccess(it.data) },
        loading: () -> Unit = { stateless.postLoading() },
        error: (Throwable) -> Unit = { stateless.postError(it) }
    ) = viewModelScope.launch {
        request()
            .onStart { loading() }
            .catch { error(it) }
            .collect { success(it)}

    }

}