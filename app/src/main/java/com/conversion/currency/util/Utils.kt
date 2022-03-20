package com.conversion.currency.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.view.inputmethod.InputMethodManager

fun isNetworkConnected(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connMgr ?: return false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network: Network = connMgr.activeNetwork ?: return false
        val capabilities = connMgr.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        val networkInfo = connMgr.activeNetworkInfo ?: return false
        return networkInfo.isAvailable && (networkInfo.isConnected || networkInfo.isConnectedOrConnecting)
    }
}

fun hideKeyboard(activity: Activity) {
    if (activity.window != null) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }
}

fun checkInputValue(text: String): Double {
    val parseVal: Double =
        if (text.isEmpty()) 1.0
        else text.toDoubleOrNull() ?: 1.0
    return if (parseVal > 0.0) parseVal else 1.0
}