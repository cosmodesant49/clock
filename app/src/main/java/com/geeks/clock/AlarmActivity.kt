package com.geeks.clock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geeks.clock.databinding.ActivityAlarmBinding
import java.text.SimpleDateFormat
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

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
        calendar.set(Calendar.MINUTE, binding.timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val currentTime = Calendar.getInstance()

        if (calendar.before(currentTime)) {
            calendar.add(Calendar.DATE, 1)
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val alarmTime = sdf.format(calendar.time)

        Toast.makeText(this, "Alarm set for $alarmTime", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show()
    }
}
