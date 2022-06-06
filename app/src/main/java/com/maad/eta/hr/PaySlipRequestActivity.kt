package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivityPaySlipRequestBinding
import com.maad.eta.employee.PaySlip

class PaySlipRequestActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var slips = arrayListOf<String>()
    private var userIds = arrayListOf<String>()
    private lateinit var binding: ActivityPaySlipRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaySlipRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Pay Slip Requests"
        db = Firebase.firestore
    }

    override fun onResume() {
        super.onResume()
        slips.clear()
        db.collection("PaySlips").get().addOnSuccessListener {
            val all = it.toObjects(PaySlip::class.java)
            for (slip in all)
                if (slip.status == "pending") {
                    slips.add(slip.slipId)
                    userIds.add(slip.userId)
                }

            binding.progress.visibility = View.GONE
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, slips)
            binding.lv.adapter = adapter

            binding.lv.setOnItemClickListener { _, _, position, _ ->
                val i = Intent(this, SendPaySlipActivity::class.java)
                i.putExtra("id", userIds[position])
                i.putExtra("slipId", slips[position])
                startActivity(i)
            }

        }
    }

}