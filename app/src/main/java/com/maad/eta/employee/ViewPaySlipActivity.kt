package com.maad.eta.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.databinding.ActivityViewPaySlipBinding

class ViewPaySlipActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var approvedSlips = arrayListOf<PaySlip>()
    private var dates = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPaySlipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Pay Slip"
        db = Firebase.firestore

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db.collection("PaySlips").get().addOnSuccessListener {
            val all = it.toObjects(PaySlip::class.java)
            for (pay in all)
                if (pay.userId == id && pay.status == "approved"){
                    approvedSlips.add(pay)
                    dates.add(pay.date)
                }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dates)
            binding.list.adapter = adapter
            binding.progress.visibility = View.GONE
            binding.list.setOnItemClickListener { _, _, position, _ ->
                val i = Intent(this, PaySlipDetailsActivity::class.java)
                i.putExtra("paySlip", approvedSlips[position])
                startActivity(i)
            }

        }

    }
}