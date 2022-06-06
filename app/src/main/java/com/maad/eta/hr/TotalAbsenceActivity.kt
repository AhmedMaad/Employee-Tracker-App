package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.R
import com.maad.eta.databinding.ActivityTotalAbsenceBinding
import com.maad.eta.employee.Tracker

class TotalAbsenceActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var tracks = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTotalAbsenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Total Absence"
        db = Firebase.firestore

        //show list of sign in/out
        //Note: You will need to send the "month" in the sign in/out activity to get previous month data
        db.collection("trackers").get().addOnSuccessListener {
            val all = it.toObjects(Tracker::class.java)
            for (tracking in all)
                if (tracking.empId == intent.getStringExtra("id"))
                    tracks.add("${tracking.date}\n${tracking.time}")

            val adapter = ArrayAdapter(this, R.layout.sign_in_check_list_item, tracks)
            binding.list.adapter = adapter
            binding.progress.visibility = View.GONE
        }

    }

}