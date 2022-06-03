package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.eta.databinding.ActivitySignInOutBinding

class SignInOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Sign In/Out"
    }
}