package com.maad.eta.employee

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class SignInOutActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var scanner: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val binding = ActivitySignInOutBinding.inflate(layoutInflater)
        title = "Sign In/Out"
        scanner = ZXingScannerView(this)
        setContentView(scanner)
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
        // Do something with the result here
        Log.d("trace", rawResult!!.text) // Prints scan results
        Log.d(
            "trace",
            rawResult.barcodeFormat.toString()
        )  // Prints the scan format (qrcode, pdf417 etc.)
        // If you would like to resume scanning, call this method below:
        scanner.resumeCameraPreview(this)
        //when "ETA" is detected then send to firebase the date and user id
    }

}