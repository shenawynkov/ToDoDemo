package com.shenawynkov.tododemo

import com.shenawynkov.tododemo.ConnectivityReceiver.ConnectivityReceiverListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.shenawynkov.tododemo.ConnectivityReceiver
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityReceiver internal constructor(private val mConnectivityReceiverListener: ConnectivityReceiverListener) :
    BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(context, isConnected(context))
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(context: Context?, isConnected: Boolean)
    }

    companion object {
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}