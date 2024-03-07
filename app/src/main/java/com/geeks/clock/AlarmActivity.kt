package com.geeks.clock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geeks.clock.databinding.ActivityAlarmBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        binding.setAlarmButton.setOnClickListener {
            setAlarm()
        }

        binding.cancelAlarmButton.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
        calendar.set(Calendar.MINUTE, binding.timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val currentTime = Calendar.getInstance()

        if (calendar.before(currentTime)) {
            calendar.add(Calendar.DATE, 1)
        }

        CoroutineScope(Dispatchers.IO).launch {
            setAlarmInBackground(calendar.timeInMillis)
        }
    }

    private fun cancelAlarm() {
        CoroutineScope(Dispatchers.IO).launch {
            cancelAlarmInBackground()
        }
    }

    private suspend fun setAlarmInBackground(timeInMillis: Long) {
        withContext(Dispatchers.Main) {
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )

        withContext(Dispatchers.Main) {
            Toast.makeText(this@AlarmActivity, "Alarm set", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun cancelAlarmInBackground() {
        withContext(Dispatchers.Main) {
        }
        alarmManager.cancel(pendingIntent)

        withContext(Dispatchers.Main) {
            Toast.makeText(this@AlarmActivity, "Alarm canceled", Toast.LENGTH_SHORT).show()
        }
    }
}
