package com.maad.eta.employee

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.admin.DayChooser
import com.maad.eta.databinding.ActivityNewAnnualRequestBinding

class NewAnnualRequestActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var day = ""
    private lateinit var binding: ActivityNewAnnualRequestBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAnnualRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.chooseDate.setOnClickListener {
            val dialog = DayChooser()
            dialog.show(supportFragmentManager, null)
        }

        binding.submitRequestBtn.setOnClickListener {
            if (binding.dateTv.text.toString().isEmpty())
                Toast.makeText(this, "Choose a date first", Toast.LENGTH_SHORT).show()
            else{
                val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                val id = prefs.getString("id", null)!!
                val vacation = Vacation(id, day)
                db.collection("annualVacations").add(vacation).addOnSuccessListener {
                    Toast.makeText(this, "Enjoy your vacation!", Toast.LENGTH_SHORT).show();
                    finish()
                }
            }

        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        day = "$year/${month + 1}/$dayOfMonth"
        binding.dateTv.text = day
    }

}