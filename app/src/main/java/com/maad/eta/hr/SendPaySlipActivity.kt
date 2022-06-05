package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivitySendPaySlipBinding

class SendPaySlipActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendPaySlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendPaySlipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Pay Slips"
    }

}