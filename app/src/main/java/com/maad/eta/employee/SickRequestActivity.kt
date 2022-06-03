package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivitySickRequestBinding

class SickRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Sick Request"
    }
}