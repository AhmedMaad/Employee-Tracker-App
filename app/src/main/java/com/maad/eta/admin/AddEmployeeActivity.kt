package com.maad.eta.admin

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.eta.R
import com.maad.eta.databinding.ActivityAddEmployeeBinding
import com.maad.eta.employee.Employee
import java.util.*

class AddEmployeeActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityAddEmployeeBinding
    private var imageUri: Uri? = null
    private var day = ""
    private lateinit var db: FirebaseFirestore
    private val departments = arrayOf("x", "y", "z")
    private var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departments)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.departmentSpinner.adapter = adapter

        binding.employeePicIv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 400)
        }

        binding.startDateBtn.setOnClickListener {
            val dialog = DayChooser()
            dialog.show(supportFragmentManager, null)
        }

        binding.confirmBtn.setOnClickListener {
            if (imageUri == null)
                Toast.makeText(this, "Picture is required", Toast.LENGTH_SHORT).show()
            else addEmailPass()
        }

    }

    private fun addEmailPass() {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(binding.emailEt.text.toString(), "123456")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    userId = task.result.user!!.uid
                    uploadImage()
                } else {
                    Toast.makeText(this, "Email exists", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            imageUri = data?.data
            binding.employeePicIv.setImageURI(imageUri)
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        day = "$year/${month + 1}/$dayOfMonth"
        binding.dateValueTv.text = day
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        val now = Calendar.getInstance()
        val y = now.get(Calendar.YEAR)
        val m = now.get(Calendar.MONTH) + 1

        val d = now.get(Calendar.DAY_OF_MONTH)
        val h = now.get(Calendar.HOUR_OF_DAY)
        val min = now.get(Calendar.MINUTE)
        val s = now.get(Calendar.SECOND)
        val imageName = "image: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(imageName)
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { imageUri ->
                    addEmployee(imageUri!!)
                }
            }
    }

    private fun addEmployee(imageUri: Uri) {
        val selectedRb: RadioButton = findViewById(binding.rg.checkedRadioButtonId)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val adminId = prefs.getString("id", null)!!

        val employee = Employee(
            binding.etName.text.toString(),
            day,
            binding.salaryEt.text.toString().toDouble(),
            departments[binding.departmentSpinner.selectedItemPosition],
            selectedRb.text.toString(),
            imageUri.toString(),
            adminId,
            binding.emailEt.text.toString()
        )

        db
            .collection("employees")
            .document(userId)
            .set(employee)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Employee Added!", Toast.LENGTH_LONG).show()
                resetFields()
            }

    }

    fun resetFields() {
        binding.etName.text!!.clear()
        binding.emailEt.text!!.clear()
        binding.dateValueTv.text = "Date shows here"
        binding.salaryEt.text!!.clear()
        binding.departmentSpinner.setSelection(0)
        binding.rg.check(R.id.full_time_rb)
        binding.employeePicIv.setImageResource(R.drawable.ic_add_emp_pink)
    }

}