package com.vivek.inventorymanagement.setup.api.client

import com.vivek.inventorymanagement.data.api.clients.IHttpClient
import com.vivek.inventorymanagement.data.api.config.InventoryApiConfig
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class FakeApiClient : IHttpClient() {
    override fun getHttpClient(): Retrofit {
        return Retrofit.Builder().baseUrl(InventoryApiConfig.GET_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}