package com.maad.eta.hr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.R
import com.maad.eta.databinding.ActivityViewSickRequestBinding
import com.maad.eta.employee.SickRequest

class ViewSickRequestActivity : AppCompatActivity(), SickRequestAdapter.ItemClickListener {

    private lateinit var binding: ActivityViewSickRequestBinding
    private lateinit var db: FirebaseFirestore
    private var requests = arrayListOf<SickRequest>()
    private var currentDays = 0L
    private lateinit var adapter: SickRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Sick Requests"
        db = Firebase.firestore

        db.collection("SickRequests").get().addOnSuccessListener {
            val all = it.toObjects(SickRequest::class.java)
            for (request in all)
                if (request.status == "Pending")
                    requests.add(request)

            adapter = SickRequestAdapter(this, requests, this)
            binding.rv.adapter = adapter
            binding.progress.visibility = View.GONE
        }

    }

    override fun onImageClick(position: Int) {
        val i = Intent(this, FullImageActivity::class.java)
        i.putExtra("image", requests[position].picture)
        startActivity(i)
    }

    override fun onAcceptBtnClick(position: Int) {
        val v = layoutInflater.inflate(R.layout.sick_days_layout, null)
        val passET = v.findViewById<EditText>(R.id.number_et)
        val builder = AlertDialog.Builder(this)
        builder
            .setView(v)
            .setPositiveButton("Confirm") { _, _ ->

                db.collection("employees").document(requests[position].userId).get()
                    .addOnSuccessListener {
                        currentDays = it.getLong("sicknessDays")!!
                        val days = passET.text.toString().toLong()
                        val map = HashMap<String, Long>()
                        map["sicknessDays"] = (days + currentDays)
                        db.collection("employees").document(requests[position].userId)
                            .update(map as Map<String, Long>).addOnSuccessListener {
                                val statusMap = HashMap<String, String>()
                                statusMap["status"] = "approved"
                                db.collection("SickRequests").document(requests[position].requestId)
                                    .update(statusMap as Map<String, String>).addOnSuccessListener {
                                        requests.removeAt(position)
                                        adapter.notifyItemRemoved(position)
                                    }
                            }
                    }
            }
            .setNegativeButton("Dismiss") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onRejectBtnClick(position: Int) {
        val statusMap = HashMap<String, String>()
        statusMap["status"] = "rejected"
        db.collection("SickRequests").document(requests[position].requestId)
            .update(statusMap as Map<String, String>).addOnSuccessListener {
                requests.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
    }

}