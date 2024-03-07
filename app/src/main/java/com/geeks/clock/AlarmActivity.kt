package com.geeks.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.clock.databinding.ActivityAlarmBinding
import com.geeks.clock.databinding.ActivityMainBinding

class AlarmActivity : AppCompatActivity() {
    private val binding: ActivityAlarmBinding by lazy {
        ActivityAlarmBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}