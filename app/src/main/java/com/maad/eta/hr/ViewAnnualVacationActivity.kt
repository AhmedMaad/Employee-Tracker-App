package com.maad.eta.hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.R
import com.maad.eta.databinding.ActivityViewAnnualVacationBinding
import com.maad.eta.employee.Vacation

class ViewAnnualVacationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAnnualVacationBinding
    private lateinit var db: FirebaseFirestore
    private var allVacations = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnnualVacationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Annual Vacations"
        db = Firebase.firestore

        db.collection("annualVacations").get().addOnSuccessListener {
            val all = it.toObjects(Vacation::class.java)
            for (vacation in all)
                allVacations.add("${vacation.name}\n${vacation.date}")

            val adapter = ArrayAdapter(this, R.layout.sign_in_check_list_item, allVacations)
            binding.list.adapter = adapter
            binding.progress.visibility = View.GONE
        }

    }

}