package com.stylingandroid.connectivity.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    networkCapabilitiesLiveData: NetworkCapabilitiesLiveData
) : ViewModel() {

    private val mapper = NetworkCapabilitiesMapper()

    val connectionState: LiveData<ConnectionState> =
        Transformations.map(networkCapabilitiesLiveData) { capabilities ->
            mapper.map(capabilities)
        }
}
