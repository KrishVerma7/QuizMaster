package com.example.quizmastercheezycode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmastercheezycode.R
import com.example.quizmastercheezycode.activities.adapters.OptionAdapter
import com.example.quizmastercheezycode.activities.models.Question
import com.example.quizmastercheezycode.activities.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1
    lateinit var btnPrevious: Button
    lateinit var btnNext: Button
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setupFirestore()
        setupEventListener()
    }

    private fun setupEventListener() {
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnPrevious.setOnClickListener(){
            index--
            bindViews()
        }
        btnNext.setOnClickListener{
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener{
            Log.d("FINAL QUIZ", questions.toString())

            val intent = Intent (this,ResultActivity::class.java)
            val json= Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    //this function fetches the quizzes from firestore of a particular date selected by datePicker
    private fun setupFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes2").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        val description: TextView = findViewById(R.id.description)
        val optionList: RecyclerView = findViewById(R.id.optionList)
        btnPrevious= findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnPrevious.visibility = View.GONE
        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if (index == 1) { //first question
            btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size) { //last question
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        } else { //middle questions
            btnNext.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }

    }
}