package com.maad.eta.employee

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.eta.databinding.ActivitySickRequestBinding
import java.util.*

class SickRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySickRequestBinding
    private var imageUri: Uri? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySickRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Sick Request"
        db = Firebase.firestore

        binding.iv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 400)
        }

        binding.submitRequestBtn.setOnClickListener {
            if (imageUri == null)
                Toast.makeText(this, "Picture is required", Toast.LENGTH_SHORT).show()
            else uploadImage()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            imageUri = data?.data
            binding.iv.setImageURI(imageUri)
            binding.searchIv.visibility = View.INVISIBLE
        }
    }

    private fun uploadImage() {
        binding.progress.visibility = View.VISIBLE
        binding.submitRequestBtn.visibility = View.INVISIBLE
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
                    submitRequest(imageUri!!)
                }
            }
    }

    private fun submitRequest(imageUri: Uri) {
        val details = binding.detailsEt.text.toString()
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!
        val sickRequest = SickRequest(id, imageUri.toString(), details)
        db.collection("SickRequests").add(sickRequest).addOnSuccessListener {
            val map = HashMap<String, String>()
            map["requestId"] = it.id
            it.update(map as Map<String, String>).addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                binding.submitRequestBtn.visibility = View.VISIBLE
                Toast.makeText(this, "Request Submitted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


}