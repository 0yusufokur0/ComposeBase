package com.resurrection.composebase.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resurrection.composebase.data.model.CryptoList
import com.resurrection.composebase.data.model.CryptoListItem
import com.resurrection.composebase.data.repository.CryptoRepository
import com.resurrection.composebase.util.resource.*
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

    var cryptoList = mutableStateResourceOf<CryptoList>()


/*
    var testCryptoState = TestResource<List<CryptoListItem>>()
*/

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun loadCryptos() =fetchStateResource(
        state = cryptoList,
        request = { repository.getCryptoList() },
    )

    fun <T> fetchStateResource(
        condition: Boolean = true,
        state: MutableState<StateResource<T>>,
        request: suspend () -> Flow<Resource<T>>,
        success: (Resource<T>) -> Unit = { state.postSuccess(it.data) },
        loading: () -> Unit = { state.postLoading(true) },
        error: (Throwable) -> Unit = { state.postError(it.message) }
    ) = viewModelScope.launch {
            request()
                .onStart { loading() }
                .catch { error(it) }
                .collect { success(it)}

    }




/*
    fun searchCryptoList(query: String) {
        val listToSearch:List<CryptoListItem> = if(isSearchStarting) {
            cryptoList.value.data!!.value!!
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                cryptoList.postSuccess(initialCryptoList)
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialCryptoList = cryptoList.data!!
                isSearchStarting = false
            }
            cryptoList.value.data.value = results
        }
    }
*/

}