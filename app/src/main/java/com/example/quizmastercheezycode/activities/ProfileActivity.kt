package com.example.quizmastercheezycode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizmastercheezycode.R
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val txtEmail:TextView = findViewById(R.id.txtEmail)
        val btnLogout:Button = findViewById(R.id.btnLogout)

        firebaseAuth = FirebaseAuth.getInstance()
        txtEmail.text = firebaseAuth.currentUser?.email

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}