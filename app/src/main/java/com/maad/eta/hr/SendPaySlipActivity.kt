package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivitySendPaySlipBinding
import com.maad.eta.employee.Employee
import com.maad.eta.employee.PaySlip
import java.util.*

class SendPaySlipActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendPaySlipBinding
    private lateinit var db: FirebaseFirestore
    private var salary = 0.0

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
                salary = e.salary
                binding.grossSalaryValueTv.text = salary.toString()
                binding.sickValueTv.text = e.sicknessDays.toString()
            }

        binding.absenceKeyTv.setOnClickListener {
            val i = Intent(this, TotalAbsenceActivity::class.java)
            i.putExtra("id", intent.getStringExtra("id")!!)
            startActivity(i)
        }

        binding.sendBtn.setOnClickListener {

            val now = Calendar.getInstance()
            val y = now.get(Calendar.YEAR)
            val m = now.get(Calendar.MONTH) + 1

            val d = now.get(Calendar.DAY_OF_MONTH)
            val h = now.get(Calendar.HOUR_OF_DAY)
            val min = now.get(Calendar.MINUTE)
            val s = now.get(Calendar.SECOND)
            val date = "Slip Date: $d-$m-$y $h:$min:$s"

            val bonus = binding.bonusEt.text.toString().toInt()
            val social = binding.socialEt.text.toString().toInt()
            val bus = binding.busCb.isChecked
            val box = binding.boxEt.text.toString().toInt()
            val health = binding.healthEt.text.toString().toInt()
            val absence = binding.absenceEt.text.toString().toInt()

            val slip = PaySlip(
                intent.getStringExtra("id")!!,
                intent.getStringExtra("slipId")!!,
                "approved",
                bonus,
                social,
                bus,
                box,
                health,
                absence,
                date
            )
            db.collection("PaySlips").document(intent.getStringExtra("slipId")!!)
                .set(slip).addOnSuccessListener {
                    Toast.makeText(this, "Pay Slip sent successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }

        }

    }

}