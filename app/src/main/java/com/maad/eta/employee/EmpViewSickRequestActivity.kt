package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivityEmpViewSickRequestBinding

class EmpViewSickRequestActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var userSickRequests = arrayListOf<SickRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmpViewSickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db.collection("SickRequests").get().addOnSuccessListener {
            val all = it.toObjects(SickRequest::class.java)
            for (sick in all)
                if (sick.userId == id)
                    userSickRequests.add(sick)

            val adapter = SickStatusAdapter(this, userSickRequests)
            binding.rv.adapter = adapter
            binding.progress.visibility = View.GONE

        }

    }
}