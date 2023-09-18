package com.example.quizmastercheezycode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.quizmastercheezycode.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var etEmailAddress: EditText
    lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        val btnLogin:Button = findViewById(R.id.btnLogin)
        val btnSignUp:TextView = findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            login()
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity1::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login(){
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword=findViewById(R.id.etPassword)

        val email = etEmailAddress.text.toString()
        val password = etPassword.text.toString()


        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}