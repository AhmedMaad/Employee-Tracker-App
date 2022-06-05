package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityTotalAbsenceBinding

class TotalAbsenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTotalAbsenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}