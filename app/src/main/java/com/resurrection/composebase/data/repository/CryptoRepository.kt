package com.resurrection.composebase.data.repository

import com.resurrection.composebase.data.model.Crypto
import com.resurrection.composebase.data.model.CryptoList
import com.resurrection.composebase.data.remote.CryptoAPI
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class CryptoRepository@Inject constructor(
    private val api: CryptoAPI
) {

    suspend fun getCryptoList(): Flow<com.resurrection.composebase.state.Resource<CryptoList>>  = getData {
        api.getCryptoList()
    }

    suspend fun getCrypto(id: String): com.resurrection.composebase.state.Resource<Crypto> {
        val response = try {
            api.getCrypto()
        } catch(e: Exception) {
            return com.resurrection.composebase.state.Resource.Error(Throwable("Error"))
        }
        return com.resurrection.composebase.state.Resource.Success(response)
    }
}

@JvmName("getData1")
fun <T> getData(request: suspend () -> Response<T>) =  flow { emit(getResourceByNetworkRequest { request() }) }

suspend fun <T> getResourceByNetworkRequest(request: suspend () -> Response<T>): com.resurrection.composebase.state.Resource<T> {
    try {
        val response = request()
        if (response.isSuccessful) {
            response.body()?.apply {
                return com.resurrection.composebase.state.Resource.Success(this)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return com.resurrection.composebase.state.Resource.Error(e)
    }
    return com.resurrection.composebase.state.Resource.Loading()
}