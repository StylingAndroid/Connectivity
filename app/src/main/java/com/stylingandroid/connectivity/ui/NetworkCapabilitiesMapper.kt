package com.stylingandroid.connectivity.ui

import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_LOWPAN
import android.net.NetworkCapabilities.TRANSPORT_VPN
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkCapabilities.TRANSPORT_WIFI_AWARE
import android.os.Build

class NetworkCapabilitiesMapper {

    private val transports: Map<Int, String>

    init {
        val transportMap = mutableMapOf(
            TRANSPORT_BLUETOOTH to "Bluetooth",
            TRANSPORT_CELLULAR to "Cellular",
            TRANSPORT_ETHERNET to "Ethernet",
            TRANSPORT_VPN to "VPN",
            TRANSPORT_WIFI to "Wi-Fi"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            transportMap += TRANSPORT_LOWPAN to "LoWPAN"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            transportMap += TRANSPORT_WIFI_AWARE to "Wi-Fi Aware"
        }
        transports = transportMap
    }

    fun map(capabilities: NetworkCapabilities?): ConnectionState =
        capabilities?.let {
            ConnectionState.Connected(
                signalStrength = ConnectionState.SignalStrength.get(capabilities),
                linkDownstreamBandwidthKbps = capabilities.linkDownstreamBandwidthKbps,
                linkUpstreamBandwidthKbps = capabilities.linkUpstreamBandwidthKbps,
                transports = transportNames(capabilities),
                hasInternet = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
            )
        } ?: ConnectionState.NotConnected

    private fun transportNames(capabilities: NetworkCapabilities): List<String> =
        transports.filter { entry ->
            capabilities.hasTransport(entry.key)
        }.map { it.value }
}
