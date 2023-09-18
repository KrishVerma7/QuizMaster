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

class SignUpActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var etEmailAddress:EditText
    lateinit var etPassword:EditText
    lateinit var etConfirmPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_sign_up)

        etEmailAddress=findViewById(R.id.etEmailAddress)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etConfirmPassword)

        firebaseAuth = FirebaseAuth.getInstance()

        var btnSignUp:Button=findViewById(R.id.btnSignUp)
        var btnLogin: TextView =findViewById(R.id.btnLogin)

        btnSignUp.setOnClickListener{
            signUpUser()
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser(){
        val email: String = etEmailAddress.text.toString()
        val password: String = etPassword.text.toString()
        val confirmPassword: String = etConfirmPassword.text.toString()

        if (email.isBlank()||password.isBlank()||confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword){
            Toast.makeText(this, "Passwords didn't matched",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { 
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
                }
            }
    }
}