package com.resurrection.composebase.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import com.atilsamancioglu.cryptocrazycompose.util.Resource
import com.resurrection.composebase.data.model.Crypto
import com.resurrection.composebase.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto(id)
    }
}