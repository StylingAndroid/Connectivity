package com.stylingandroid.connectivity.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import javax.inject.Inject

class NetworkCapabilitiesLiveData @Inject constructor(
    private val context: Context,
    private val connectivityManager: ConnectivityManager
) : LiveData<NetworkCapabilities>() {

    private var activeNetwork: Network? = null
    private val allNetworks = mutableMapOf<Network, NetworkCapabilities>()

    override fun onActive() {
        super.onActive()
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            callback
        )
        context.registerReceiver(
            receiver,
            IntentFilter(CONNECTIVITY_CHANGE)
        )
    }

    override fun onInactive() {
        context.unregisterReceiver(receiver)
        connectivityManager.unregisterNetworkCallback(callback)
        super.onInactive()
    }

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            allNetworks.remove(network)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            allNetworks[network] = networkCapabilities
            updateActiveNetwork()
        }
    }

    private fun updateActiveNetwork() {
        activeNetwork = connectivityManager.activeNetwork
        val capabilities = allNetworks[activeNetwork]
        if (capabilities != value) {
            postValue(capabilities)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == CONNECTIVITY_CHANGE) {
                updateActiveNetwork()
            }
        }
    }

    companion object {
        private const val CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    }
}
