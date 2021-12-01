package com.shenawynkov.tododemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobService
import com.shenawynkov.tododemo.ConnectivityReceiver.ConnectivityReceiverListener
import com.shenawynkov.tododemo.ConnectivityReceiver
import com.shenawynkov.tododemo.NetworkSchedulerService
import android.content.Intent
import android.app.job.JobParameters
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NetworkSchedulerService : JobService(), ConnectivityReceiverListener {
    private var mConnectivityReceiver: ConnectivityReceiver? = null
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created")
        mConnectivityReceiver = ConnectivityReceiver(this)
    }

    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        return START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStartJob$mConnectivityReceiver")
        registerReceiver(
            mConnectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStopJob")
        unregisterReceiver(mConnectivityReceiver)
        return true
    }

    override fun onNetworkConnectionChanged(context: Context?, isConnected: Boolean) {
        val message =
            if (isConnected) "Good! Connected to Internet" else "Sorry! Not connected to internet"
        context?.let { createNotificationChannel(it) }
        var builder = context?.let {
            NotificationCompat.Builder(it, "s")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Connection Status")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }
        with(NotificationManagerCompat.from(this)) {
            builder?.build()?.let { notify(1, it) }
        }
    }

    companion object {
        private val TAG = NetworkSchedulerService::class.java.simpleName
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "s"
            val descriptionText = "s"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("s", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}