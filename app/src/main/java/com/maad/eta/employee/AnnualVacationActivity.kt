package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityAnnualVacationBinding

class AnnualVacationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAnnualVacationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Annual Vacations"
    }
}