package com.maad.eta.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityAddEmployeeBinding

class AddEmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}