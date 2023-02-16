package com.vivek.inventorymanagement.api

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class InventoryHttpClient {
    fun getBaseAdapter(): Retrofit {
        return Retrofit.Builder().baseUrl("https://run.mocky.io/")
            .addConverterFactory(JacksonConverterFactory.create()).build()


    }
}