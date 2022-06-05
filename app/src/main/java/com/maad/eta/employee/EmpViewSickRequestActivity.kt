package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityEmpViewSickRequestBinding

class EmpViewSickRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmpViewSickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}