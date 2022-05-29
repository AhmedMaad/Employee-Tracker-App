package com.maad.eta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.eta.admin.AdminHomeActivity
import com.maad.eta.databinding.ActivityLoginBinding
import com.maad.eta.employee.EmployeeHomeActivity
import com.maad.eta.hr.HRHomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Login"
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            val mail = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()
            if (mail.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "Cannot login with empty field/s", Toast.LENGTH_SHORT).show();
            else
                auth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful)
                            userType(mail)
                        else
                            Toast.makeText(this, "Sorry, didn't find user", Toast.LENGTH_SHORT)
                                .show()
                    }
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            userType(currentUser.email!!)
    }

    private fun userType(mail: String) {
        when (mail) {
            "hr@gmail.com" ->
                startActivity(Intent(this, HRHomeActivity::class.java))
            "admin@gmail.com" ->
                startActivity(Intent(this, AdminHomeActivity::class.java))
            else ->
                startActivity(Intent(this, EmployeeHomeActivity::class.java))
        }
        finish()
    }


}