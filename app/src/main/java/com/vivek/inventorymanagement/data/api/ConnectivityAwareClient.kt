package com.vivek.inventorymanagement.data.api

import android.content.Context
import com.vivek.inventorymanagement.data.api.exception.NetworkException
import com.vivek.inventorymanagement.data.util.ApiUtility
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class ConnectivityAwareClient @Inject constructor(@ApplicationContext private val mApplicationContext: Context) :
    OkHttpClient() {

    override fun newCall(request: Request): Call {
        if (!ApiUtility.isInternetConnected(mApplicationContext)) {
            throw NetworkException()
        }
        return super.newCall(request)
    }
}