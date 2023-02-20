package com.vivek.inventorymanagement.data.api.clients

import com.vivek.inventorymanagement.data.api.ConnectivityAwareClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Inject

class InventoryHttpClient @Inject constructor(private val mConnectivityAwareClient: ConnectivityAwareClient) :
    IHttpClient() {
    override fun getHttpClient(): Retrofit {
        return Retrofit.Builder().client(mConnectivityAwareClient).baseUrl("https://run.mocky.io/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}