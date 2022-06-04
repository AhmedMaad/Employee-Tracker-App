package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityViewPaySlipBinding

class ViewPaySlipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPaySlipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Pay Slip"
    }
}