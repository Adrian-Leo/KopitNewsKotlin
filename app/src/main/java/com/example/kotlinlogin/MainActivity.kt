package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var register: TextView? = null
    private var forgot: TextView? = null
    private var emailL: EditText? = null
    private var passwordL: EditText? = null
    private var login: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        register = findViewById<View>(R.id.txtRegister) as TextView
        register!!.setOnClickListener(this)
        forgot = findViewById<View>(R.id.txtForgot) as TextView
        forgot!!.setOnClickListener(this)
        emailL = findViewById<View>(R.id.edtEmail) as EditText
        passwordL = findViewById<View>(R.id.edtPassword) as EditText
        login = findViewById<View>(R.id.btnLogin) as Button
        login!!.setOnClickListener(this)
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtRegister -> startActivity(Intent(this, RegisterUser::class.java))
            R.id.btnLogin -> LoginUser()
            R.id.txtForgot -> startActivity(Intent(this, forgotPassword::class.java))
        }
    }

    private fun LoginUser() {
        val emailLgn: String = emailL!!.text.toString().trim { it <= ' ' }
        val passwordLgn: String = passwordL!!.text.toString().trim { it <= ' ' }
        if (emailLgn.isEmpty()) {
            emailL!!.error = "Email is required! "
            emailL!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailLgn).matches()) {
            emailL!!.error = "Wrong email format! "
            emailL!!.requestFocus()
            return
        }
        if (passwordLgn.isEmpty()) {
            passwordL!!.error = "Password is required! "
            passwordL!!.requestFocus()
            return
        }
        if (passwordLgn.length < 6) {
            passwordL!!.error = "Password must more than 6 character! "
            passwordL!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        mAuth!!.signInWithEmailAndPassword(emailLgn, passwordLgn)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user.isEmailVerified) {
                            val intent = Intent(this,Home::class.java)
                            startActivity(intent)
                        } else {
                            user.sendEmailVerification()
                            Toast.makeText(this@MainActivity, "Check your email to verified your account", Toast.LENGTH_LONG).show()
                            progressBar!!.visibility = View.GONE
                            startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to login ! please check your email and password!", Toast.LENGTH_LONG).show()
                        progressBar!!.visibility = View.GONE
                    }
                }
    }
}