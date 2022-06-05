package com.maad.eta.employee

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.Result
import com.maad.eta.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*
import kotlin.collections.HashMap

class SignInOutActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var scanner: ZXingScannerView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val binding = ActivitySignInOutBinding.inflate(layoutInflater)
        title = "Sign In/Out"
        scanner = ZXingScannerView(this)
        setContentView(scanner)
        db = Firebase.firestore
    }

    override fun onResume() {
        super.onResume()
        scanner.setResultHandler(this) // Register ourselves as a handler for scan results.
        scanner.startCamera()        // Start camera on resume
    }

    override fun onPause() {
        super.onPause()
        scanner.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        val now = Calendar.getInstance()
        val y = now.get(Calendar.YEAR)
        val m = now.get(Calendar.MONTH) + 1

        val d = now.get(Calendar.DAY_OF_MONTH)
        val h = now.get(Calendar.HOUR_OF_DAY)
        val min = now.get(Calendar.MINUTE)
        val s = now.get(Calendar.SECOND)
        val date = "$d-$m-$y"
        val time = "$h:$min:$s"

        val map = HashMap<String, String>()
        map["id"] = id
        map["date"] = date
        map["time"] = time

        if (rawResult!!.text == "ETA") {
            val media = MediaPlayer.create(this, R.raw.thank_you)
            db.collection("trackers").add(map).addOnSuccessListener {
                media.start()
                Toast.makeText(this, "Thank you!", Toast.LENGTH_LONG).show()
                finish()
            }
        } else {
            val media = MediaPlayer.create(this, R.raw.try_again)
            media.start()
            Toast.makeText(this, "Please, Try Again!", Toast.LENGTH_LONG).show()
            finish()
        }

    }

}