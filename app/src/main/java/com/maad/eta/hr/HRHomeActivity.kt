package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.eta.LoginActivity
import com.maad.eta.R
import com.maad.eta.databinding.ActivityHrhomeBinding

class HRHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHrhomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "HR Home"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Firebase.auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
        return super.onOptionsItemSelected(item)
    }

}