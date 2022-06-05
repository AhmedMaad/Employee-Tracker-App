package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivityViewSickRequestBinding

class ViewSickRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewSickRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Sick Requests"
    }

}