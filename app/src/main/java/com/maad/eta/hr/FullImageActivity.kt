package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.maad.eta.R

class FullImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        val image: ImageView = findViewById(R.id.iv)
        Glide.with(this).load(intent.getStringExtra("image")).into(image)
    }
}