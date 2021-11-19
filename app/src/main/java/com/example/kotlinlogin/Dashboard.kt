package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Dashboard : AppCompatActivity() {

    private var logout: Button? = null
    private var user: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        logout = findViewById<View>(R.id.btnLogout) as Button
        logout!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@Dashboard, MainActivity::class.java))
        }
        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users")
        userID = user!!.uid
        val greetingTextView = findViewById<View>(R.id.greeting) as TextView
        val emailTextView = findViewById<View>(R.id.emailAddress) as TextView
        val nameTextView = findViewById<View>(R.id.name) as TextView
        val ageTextView = findViewById<View>(R.id.age) as TextView
        reference!!.child(userID!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile: User = snapshot.getValue<User>(User::class.java)!!
                if (userProfile != null) {
                    val fullName = userProfile.name
                    val email = userProfile.email
                    val age = userProfile.age
                    greetingTextView.text = "Welcome $fullName!"
                    nameTextView.text = fullName
                    emailTextView.text = email
                    ageTextView.text = age
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Dashboard, "Something wrong happened!", Toast.LENGTH_LONG).show()
            }
        })
    }
}