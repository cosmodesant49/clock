package com.geeks.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.geeks.clock.databinding.ActivitySecBinding
import kotlinx.coroutines.*

class SecActivity : AppCompatActivity() {
    private val binding: ActivitySecBinding by lazy {
        ActivitySecBinding.inflate(layoutInflater)
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            job = CoroutineScope(Dispatchers.Main).launch {
                startChronometer()
            }
        }

        binding.stopBtn.setOnClickListener {
            job?.cancel()
            binding.chronometer.stop()
        }
    }

    private suspend fun startChronometer() {
        binding.chronometer.base = SystemClock.elapsedRealtime()
        binding.chronometer.start()
        delay(100000)
        binding.chronometer.stop()
    }
}
