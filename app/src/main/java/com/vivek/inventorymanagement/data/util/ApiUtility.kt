package com.vivek.inventorymanagement.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

object ApiUtility {
    /**
     * [isInternetConnected] checks for internet connectivity
     * returns true if internet is connected
     * */
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val isConnected: Boolean? = connectivityManager.let { tempConnectivityManager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    tempConnectivityManager.getNetworkCapabilities(tempConnectivityManager.activeNetwork)
                networkCapabilities != null
            } else {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.let { tempNetworkInfo ->
                    tempNetworkInfo.isConnected
                }
            }
        }
        return isConnected ?: false

    }
}