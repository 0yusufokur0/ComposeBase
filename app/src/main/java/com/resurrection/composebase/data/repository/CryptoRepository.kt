package com.resurrection.composebase.data.repository

import com.resurrection.composebase.util.resource.Resource
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

    suspend fun getCryptoList(): Flow<Resource<CryptoList>>  = getData {
        api.getCryptoList()
    }

    suspend fun getCrypto(id: String): Resource<Crypto> {
        val response = try {
            api.getCrypto()
        } catch(e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}

@JvmName("getData1")
fun <T> getData(request: suspend () -> Response<T>) =  flow { emit(getResourceByNetworkRequest { request() }) }

suspend fun <T> getResourceByNetworkRequest(request: suspend () -> Response<T>): Resource<T> {
    try {
        val response = request()
        if (response.isSuccessful) {
            response.body()?.apply {
                return Resource.Success(this)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return Resource.Error(e.localizedMessage)
    }
    return Resource.Loading()
}