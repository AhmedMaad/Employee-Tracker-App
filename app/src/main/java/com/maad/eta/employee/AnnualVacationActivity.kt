package com.maad.eta.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivityAnnualVacationBinding

class AnnualVacationActivity : AppCompatActivity() {

    var vacationsConsumed = 0
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityAnnualVacationBinding
    private val history = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnualVacationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Annual Vacations"
        db = Firebase.firestore

        binding.newRequestBtn.setOnClickListener {
            if (vacationsConsumed == 21)
                Toast.makeText(this, "You have consumed all of your vacations", Toast.LENGTH_SHORT)
                    .show()
            else
                startActivity(Intent(this, NewAnnualRequestActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        db.collection("annualVacations").get().addOnSuccessListener {
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            val id = prefs.getString("id", null)!!
            history.clear()
            val vacations = it.toObjects(Vacation::class.java)
            for (vacation in vacations)
                if (vacation.userId == id)
                    history.add(vacation.date)

            if (history.size == 0) {
                binding.historyIv.visibility = View.VISIBLE
                binding.historyTv.visibility = View.VISIBLE
                binding.historyLv.visibility = View.INVISIBLE
            } else {
                binding.historyIv.visibility = View.INVISIBLE
                binding.historyTv.visibility = View.INVISIBLE
                binding.historyLv.visibility = View.VISIBLE
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, history)
                binding.historyLv.adapter = adapter
            }

            vacationsConsumed = history.size
            binding.consumedValueTv.text = vacationsConsumed.toString()
            binding.remainingValueTv.text = (21 - vacationsConsumed).toString()
            binding.progress.visibility = View.INVISIBLE
        }
    }

}