package com.resurrection.composebase.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.resurrection.composebase.util.core.BaseViewModel
import com.resurrection.composebase.state.mutableStatelessResourceOf
import com.resurrection.composebase.data.model.CryptoList
import com.resurrection.composebase.data.repository.CryptoRepository
import com.resurrection.composebase.util.resource.stateful.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : BaseViewModel() {

    var cryptoList = mutableStatelessResourceOf<CryptoList>()

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
        request: suspend () -> Flow<com.resurrection.composebase.state.Resource<T>>,
        success: (com.resurrection.composebase.state.Resource<T>) -> Unit = { stateful.postSuccess(it.data) },
        loading: () -> Unit = { stateful.postLoading() },
        error: (Throwable) -> Unit = { stateful.postError(it) }
    ) = viewModelScope.launch {
            request()
                .onStart { loading() }
                .catch { error(it) }
                .collect { success(it)}

    }


}