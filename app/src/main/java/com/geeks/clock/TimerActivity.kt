package com.geeks.clock

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geeks.clock.databinding.ActivityTimerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerActivity : AppCompatActivity() {

    private val binding: ActivityTimerBinding by lazy {
        ActivityTimerBinding.inflate(layoutInflater)
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener { onClickStart() }
    }

    private fun onClickStart() {
        if (job == null || job?.isActive != true) {
            job = CoroutineScope(Dispatchers.Main).launch {
                startCountdown()
                showToast("Timer finished!")
            }
        }
    }

    private suspend fun startCountdown() {
        var number: Int
        try {
            number = binding.etNumber.text.toString().toInt()
        } catch (e: NumberFormatException) {
            number = 0
        }

        while (isActive && number >= 0) {
            val hours = number / 3600
            val minutes = (number % 3600) / 60
            val seconds = number % 60
            val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            withContext(Dispatchers.Main) {
                binding.countdownTv.text = formattedTime
            }
            number--
            delay(1000)
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}