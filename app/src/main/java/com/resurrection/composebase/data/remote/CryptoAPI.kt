package com.resurrection.composebase.data.remote

import com.resurrection.composebase.data.model.Crypto
import com.resurrection.composebase.data.model.CryptoList
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptoList(
    ): Response<CryptoList>

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/crypto.json")
    suspend fun getCrypto(
    ): Crypto
}