package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivitySendPaySlipBinding
import com.maad.eta.employee.Employee

class SendPaySlipActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendPaySlipBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendPaySlipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        title = "Pay Slips"

        db.collection("employees").document(intent.getStringExtra("id")!!).get()
            .addOnSuccessListener {
                val e = it.toObject(Employee::class.java)!!
                binding.nameValueTv.text = e.name
                binding.typeValueTv.text = e.employmentType
                binding.positionValueTv.text = e.position
                binding.grossSalaryValueTv.text = e.salary.toString()
                binding.sickValueTv.text = e.sicknessDays.toString()

            }

        //send the date along with user id, and delete item from recycler view, change status

        binding.absenceKeyTv.setOnClickListener {
            val i = Intent(this, TotalAbsenceActivity::class.java)
            i.putExtra("id", intent.getStringExtra("id")!!)
            startActivity(i)
        }

    }

}