package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityPaySlipBinding

class PaySlipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaySlipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Pay Slip"
    }
}