package com.maad.eta.employee

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.eta.LoginActivity
import com.maad.eta.R
import com.maad.eta.databinding.ActivityEmployeeHomeBinding


class EmployeeHomeActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmployeeHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        title = "Home"

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        )
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 101)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db.collection("employees").document(id).get().addOnSuccessListener {
            val employee = it.toObject(Employee::class.java)!!
            Glide.with(this).load(employee.picture).into(binding.ppIv)
            name = employee.name
            binding.nameTv.append("\n$name")
        }

        binding.annualCv.setOnClickListener {
            val i = Intent(this, AnnualVacationActivity::class.java)
            i.putExtra("name", name)
            startActivity(i)
        }

        binding.sickCv.setOnClickListener {
            startActivity(Intent(this, SickRequestActivity::class.java))
        }

        binding.signCv.setOnClickListener {
            startActivity(Intent(this, SignInOutActivity::class.java))
        }

        binding.viewSlipCv.setOnClickListener {
            startActivity(Intent(this, ViewPaySlipActivity::class.java))
        }

        binding.viewSickCv.setOnClickListener {
            startActivity(Intent(this, EmpViewSickRequestActivity::class.java))
        }

        binding.slipCv.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.slipCv.visibility = View.INVISIBLE
            val slip = PaySlip(id)
            db.collection("PaySlips").add(slip).addOnSuccessListener {
                it.update("slipId", it.id).addOnSuccessListener {
                    binding.progress.visibility = View.INVISIBLE
                    binding.slipCv.visibility = View.VISIBLE
                    Toast.makeText(this, "Request sent to HR", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.emp_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> {
                Firebase.auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            R.id.change_pass_item -> {
                showDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val v = layoutInflater.inflate(R.layout.change_pass_layout, null)
        val passET = v.findViewById<EditText>(R.id.pass_et)
        val builder = AlertDialog.Builder(this)
        builder
            .setView(v)
            .setPositiveButton("Proceed") { _, _ ->
                val newPassword = passET.text.toString()
                val user = FirebaseAuth.getInstance().currentUser
                user!!.updatePassword(newPassword)
                    .addOnCompleteListener { task: Task<Void?> ->
                        if (task.isSuccessful) Toast.makeText(
                            this,
                            "Password Changed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .setNegativeButton("Dismiss") { dialog, _ -> dialog.dismiss() }
            .show()
    }

}