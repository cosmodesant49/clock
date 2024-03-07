package com.geeks.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm received!")
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_LONG).show()

    }
}
