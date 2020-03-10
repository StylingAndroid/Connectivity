package com.stylingandroid.connectivity.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import com.stylingandroid.connectivity.R
import com.stylingandroid.connectivity.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private lateinit var colourSpan: CharacterStyle

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        colourSpan = ForegroundColorSpan(
            getColor(R.color.colorPrimary)
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.connectionState.observe(this, connectionStateObserver)
    }

    override fun onPause() {
        viewModel.connectionState.removeObserver(connectionStateObserver)
        super.onPause()
    }

    private val connectionStateObserver = Observer<ConnectionState> { connectionState ->
        TransitionManager.beginDelayedTransition(binding.root)
        when (connectionState) {
            is ConnectionState.Connected -> renderConnected(connectionState)
            ConnectionState.NotConnected -> renderNotConnected()
        }
    }

    private fun renderConnected(connected: ConnectionState.Connected) {
        binding.connectedGroup.visibility = View.VISIBLE
        binding.signalStrengthGroup.visibility = View.VISIBLE
        binding.noConnection.visibility = View.GONE
        binding.transports.text = connected.transports.joinToString()
        binding.signalStrengthGroup.visibility =
            if (connected.signalStrength is ConnectionState.SignalStrength.NoSignalStrength) {
                View.GONE
            } else {
                View.VISIBLE
            }
        binding.signalStrength.text = "${connected.signalStrength.strength}"
        binding.hasInternet.setImageResource(
            if (connected.hasInternet) R.drawable.ic_yes else R.drawable.ic_no
        )
        binding.hasInternet.contentDescription = getString(
            if (connected.hasInternet) R.string.has_internet_yes else R.string.has_internet_no
        )
        binding.downSpeed.text = formatSpeed(connected.linkDownstreamBandwidthKbps)
        binding.upSpeed.text = formatSpeed(connected.linkUpstreamBandwidthKbps)
    }

    private fun renderNotConnected() {
        binding.connectedGroup.visibility = View.GONE
        binding.signalStrengthGroup.visibility = View.GONE
        binding.noConnection.visibility = View.VISIBLE
    }

    private fun formatSpeed(speed: Int) =
        when {
            speed > ONE_MILLION -> getString(R.string.speed_gbps, speed.toFloat() / ONE_MILLION)
            speed > ONE_THOUSAND -> getString(R.string.speed_mbps, speed.toFloat() / ONE_THOUSAND)
            else -> getString(R.string.speed_kbps, speed)
        }.let { rawString ->
            spanSpeedString(rawString)
        }

    private fun spanSpeedString(rawString: String) =
        SpannableStringBuilder(rawString).apply {
            numberRegex.findAll(rawString).forEach { result ->
                set(result.range, colourSpan)
            }
        }

    companion object {
        private const val ONE_THOUSAND = 1000
        private const val ONE_MILLION = 1000000

        private val numberRegex = Regex("^\\d*\\.\\d+\\s|\\d+\\.\\d*\\s\$")
    }
}
