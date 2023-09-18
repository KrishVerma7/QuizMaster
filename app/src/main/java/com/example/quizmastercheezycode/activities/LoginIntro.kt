package com.example.quizmastercheezycode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.quizmastercheezycode.R
import com.google.firebase.auth.FirebaseAuth

class LoginIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){          //Firebase property to see if user is already logged in
            Toast.makeText(this,"User is already logged in!",Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        var btnGetStarted : Button= findViewById(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener{
            redirect("LOGIN")
        }
    }

    private fun redirect(name:String){      //redirects to particular activity according to condition
        val intent = when(name){
            "LOGIN"-> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}