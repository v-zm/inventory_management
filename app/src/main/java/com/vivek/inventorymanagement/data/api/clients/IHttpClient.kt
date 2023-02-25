package com.vivek.inventorymanagement.data.api.clients

import retrofit2.Retrofit

interface IHttpClient {
    fun getHttpClient(): Retrofit;
}