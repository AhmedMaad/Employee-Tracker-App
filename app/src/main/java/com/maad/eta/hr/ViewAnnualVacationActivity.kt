package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityViewAnnualVacationBinding

class ViewAnnualVacationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAnnualVacationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnnualVacationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Annual Vacations"
    }

}