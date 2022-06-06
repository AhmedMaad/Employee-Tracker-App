package com.maad.eta.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivityPaySlipDetailsBinding

class PaySlipDetailsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var netSalary = 0.0
    private var grossSalary = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaySlipDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        title = "Pay Slip Details"

        val p = intent.getParcelableExtra<PaySlip>("paySlip")!!

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db.collection("employees").document(id).get()
            .addOnSuccessListener {
                val e = it.toObject(Employee::class.java)!!
                binding.nameValueTv.text = e.name
                binding.typeValueTv.text = e.employmentType
                binding.positionValueTv.text = e.position
                grossSalary = e.salary
                binding.grossSalaryValueTv.text = grossSalary.toString()
                binding.sickValueTv.text = e.sicknessDays.toString()
                binding.bonusTv.text = p.bonus.toString()
                binding.socialTv.text = p.socialInsurance.toString()
                binding.busValueTv.text = when (p.bus) {
                    true -> "YES"
                    else -> "NO"
                }
                binding.boxTv.text = p.box.toString()
                binding.healthTv.text = p.healthInsurance.toString()
                binding.absenceTv.text = p.absence.toString()

                netSalary =
                    grossSalary + p.bonus - p.socialInsurance - p.box - p.healthInsurance - p.absence
                if (p.bus) netSalary -= 75
                binding.netSalaryValueTv.text = netSalary.toString()
            }

    }
}