package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.LoginActivity
import com.maad.eta.R
import com.maad.eta.databinding.ActivityHrhomeBinding
import com.maad.eta.employee.SickRequest

class HRHomeActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHrhomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "HR Home"
        db = Firebase.firestore

        binding.annualCv.setOnClickListener {
            startActivity(Intent(this, ViewAnnualVacationActivity::class.java))
        }

        binding.sickCv.setOnClickListener {
            startActivity(Intent(this, ViewSickRequestActivity::class.java))
        }

        binding.slipCv.setOnClickListener {
            startActivity(Intent(this, SendPaySlipActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hr_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> {
                Firebase.auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            R.id.restore_item -> restoreEverything()
        }

        return super.onOptionsItemSelected(item)
    }

    /*
    * This function should be used at the beginning of each year
    * */
    private fun restoreEverything() {
        db.collection("SickRequests").get().addOnSuccessListener { x ->
            val allSick = x.documents
            for (sick in allSick)
                db.collection("SickRequests").document(sick.getString("requestId")!!)
                    .delete()
            db.collection("annualVacations").get().addOnSuccessListener { y ->
                val allVacations = y.documents
                for (sick in allVacations)
                    db.collection("annualVacations").document(sick.getString("requestId")!!)
                        .delete()
                db.collection("trackers").get().addOnSuccessListener { z ->
                    val trackers = z.documents
                    for (sick in trackers)
                        db.collection("trackers").document(sick.getString("requestId")!!)
                            .delete()
                    Toast.makeText(this, "Everything is now deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}