package com.vivek.inventorymanagement.data.api.clients

import retrofit2.Retrofit

abstract class IHttpClient {
    abstract fun getHttpClient(): Retrofit;
}