package com.geeks.clock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.clock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sec.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecActivity::class.java))
        }

        binding.timer.setOnClickListener {
            startActivity(Intent(this@MainActivity, TimerActivity::class.java))
        }

        binding.alarm.setOnClickListener {
            startActivity(Intent(this@MainActivity, AlarmActivity::class.java))
        }
    }
}
