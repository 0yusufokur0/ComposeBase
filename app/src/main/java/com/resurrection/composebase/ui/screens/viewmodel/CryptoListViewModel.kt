package com.resurrection.composebase.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resurrection.composebase.data.model.CryptoList
import com.resurrection.composebase.data.model.CryptoListItem
import com.resurrection.composebase.data.repository.CryptoRepository
import com.resurrection.composebase.util.resource.*
import com.resurrection.composebase.util.resource.stateful.*
import com.resurrection.composebase.util.resource.stateless.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStatelessResourceOf<CryptoList>(Status.LOADING)

    init {
        loadCryptos()
    }

    fun loadCryptos() = fetchStatelessResource(
        stateless = cryptoList,
        request = { repository.getCryptoList() },
    )

    fun <T> fetchStatefulResource(
        condition: Boolean = true,
        stateful: MutableState<StatefulResource<T>>,
        request: suspend () -> Flow<Resource<T>>,
        success: (Resource<T>) -> Unit = { stateful.postSuccess(it.data) },
        loading: () -> Unit = { stateful.postLoading() },
        error: (Throwable) -> Unit = { stateful.postError(it) }
    ) = viewModelScope.launch {
            request()
                .onStart { loading() }
                .catch { error(it) }
                .collect { success(it)}

    }

    fun <T> fetchStatelessResource(
        condition: Boolean = true,
        stateless: MutableState<StatelessResource<T>>,
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