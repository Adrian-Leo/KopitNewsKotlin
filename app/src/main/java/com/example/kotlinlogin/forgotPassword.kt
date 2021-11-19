package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class forgotPassword : AppCompatActivity() {
    private var emailRst: EditText? = null
    private var resetBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        emailRst = findViewById<View>(R.id.edtEmailRst) as EditText
        resetBtn = findViewById<View>(R.id.btnReset) as Button
        resetBtn!!.setOnClickListener { resetPassword() }
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        mAuth = FirebaseAuth.getInstance()
    }

    private fun resetPassword() {
        val email: String = emailRst!!.text.toString()
        if (email == null) {
            emailRst!!.error = "Email is required!"
            emailRst!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRst!!.error = "please provide valid email"
        }
        progressBar!!.visibility = View.VISIBLE
        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@forgotPassword, "Check your email to reset password !", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@forgotPassword, MainActivity::class.java))
                } else {
                    Toast.makeText(this@forgotPassword, "Something wrong! please input valid email!", Toast.LENGTH_LONG).show()
                }
            }
    }
}