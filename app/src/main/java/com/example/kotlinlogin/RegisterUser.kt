package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterUser : AppCompatActivity(), View.OnClickListener{

    private var mAuth: FirebaseAuth? = null
    private var bannerV: TextView? = null
    private var registerUserV: TextView? = null
    private var edtNameV: EditText? = null
    private var edtAgeV: EditText? = null
    private var edtEmailV: EditText? = null
    private var edtPasswordV: EditText? = null
    private var progressBarV: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        mAuth = FirebaseAuth.getInstance()
        bannerV = findViewById<View>(R.id.banner) as TextView
        bannerV!!.setOnClickListener(this)
        registerUserV = findViewById<View>(R.id.btnRegister) as Button
        registerUserV!!.setOnClickListener(this)
        edtNameV = findViewById<View>(R.id.edtName) as EditText
        edtAgeV = findViewById<View>(R.id.edtAge) as EditText
        edtEmailV = findViewById<View>(R.id.edtEmail) as EditText
        edtPasswordV = findViewById<View>(R.id.edtPassword) as EditText
        progressBarV = findViewById<View>(R.id.progress) as ProgressBar
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.banner -> startActivity(Intent(this, MainActivity::class.java))
                R.id.btnRegister -> registerUser()
            }
        }
    }

    private fun registerUser() {
        val name: String = edtNameV!!.text.toString().trim { it <= ' ' }
        val age: String = edtAgeV!!.text.toString().trim { it <= ' ' }
        val email: String = edtEmailV!!.text.toString().trim { it <= ' ' }
        val password: String = edtPasswordV!!.text.toString().trim { it <= ' ' }
        if (name.isEmpty()) {
            edtNameV!!.error = "Name is Required"
            edtNameV!!.requestFocus()
            return
        }
        if (age.isEmpty()) {
            edtAgeV!!.error = "age is Required"
            edtAgeV!!.requestFocus()
            return
        }
        if (email.isEmpty()) {
            edtEmailV!!.error = "Email is Required"
            edtEmailV!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailV!!.error = "Please Input Valid Email"
            edtEmailV!!.requestFocus()
            return
        }
        if (password.isEmpty()) {
            edtPasswordV!!.error = "Name is Required"
            edtPasswordV!!.requestFocus()
            return
        }
        if (password.length < 6) {
            edtEmailV!!.error = "Password must be more than 6 character!"
            edtEmailV!!.requestFocus()
            return
        }
        progressBarV!!.visibility = View.VISIBLE
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(name, email, age)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().uid!!)
                        .setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@RegisterUser, "User has been registered sucessfuly!", Toast.LENGTH_LONG).show()
                                progressBarV!!.visibility = View.GONE
                                startActivity(Intent(this@RegisterUser, MainActivity::class.java))
                            } else {
                                Toast.makeText(this@RegisterUser, "Failed to register! Try again!1", Toast.LENGTH_LONG).show()
                                progressBarV!!.visibility = View.GONE
                            }
                        }
                } else {
                    Toast.makeText(this@RegisterUser, "Failed to register! Try again!2", Toast.LENGTH_LONG).show()
                    progressBarV!!.visibility = View.GONE
                }
            }
    }


}