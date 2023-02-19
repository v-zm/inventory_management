package com.vivek.inventorymanagement.data.api.clients

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Inject

class InventoryHttpClient @Inject constructor() : IHttpClient() {
    override fun getBaseAdapter(): Retrofit {
        return Retrofit.Builder().baseUrl("https://run.mocky.io/")
            .addConverterFactory(JacksonConverterFactory.create()).build()
    }
}