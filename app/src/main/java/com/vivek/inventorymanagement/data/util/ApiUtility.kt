package com.vivek.inventorymanagement.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

class ApiUtility {
companion object{
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.let { tempConnectivityManager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    tempConnectivityManager.getNetworkCapabilities(tempConnectivityManager.activeNetwork)
                if (networkCapabilities != null) {
                    return true
                } else {
                    //TODO::
                }

            } else {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.let { tempNetworkInfo ->
                    return tempNetworkInfo.isConnected
                }
            }
        }
        return false

    }
}
}