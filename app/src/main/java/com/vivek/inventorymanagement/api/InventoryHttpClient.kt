package com.vivek.inventorymanagement.api

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class InventoryHttpClient {
    public fun getBaseAdapter(): Retrofit {

        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://run.mocky.io/")
            .addConverterFactory(JacksonConverterFactory.create()).build()
        return retrofit


    }
}