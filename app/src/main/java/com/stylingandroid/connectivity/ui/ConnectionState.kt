package com.stylingandroid.connectivity.ui

import android.net.NetworkCapabilities
import android.os.Build

sealed class ConnectionState {

    object NotConnected : ConnectionState()

    data class Connected(
        val signalStrength: SignalStrength,
        val linkDownstreamBandwidthKbps: Int,
        val linkUpstreamBandwidthKbps: Int,
        val transports: List<String>,
        val hasInternet: Boolean
    ) : ConnectionState()

    sealed class SignalStrength(val strength: Int) {
        class DbSignalStrength(strength: Int) : SignalStrength(strength)
        object NoSignalStrength : SignalStrength(UNAVAILABLE)

        companion object {
            const val UNAVAILABLE = Int.MIN_VALUE
            fun get(capabilities: NetworkCapabilities): SignalStrength =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                    capabilities.signalStrength != UNAVAILABLE) {
                    DbSignalStrength(capabilities.signalStrength)
                } else {
                    NoSignalStrength
                }
        }
    }
}
