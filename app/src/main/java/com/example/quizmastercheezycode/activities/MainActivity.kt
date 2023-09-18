package com.example.quizmastercheezycode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmastercheezycode.R
import com.example.quizmastercheezycode.activities.adapters.QuizAdapter
import com.example.quizmastercheezycode.activities.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList=  mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
        setupDatePicker()
    }

    private fun setupDatePicker() {
        val btnDatePicker:FloatingActionButton = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }


    fun setUpViews(){
        setupFirestore()
        setUpDrawerLayout()
        setUpRecyclerView()
    }

    private fun setupFirestore(){
        firestore= FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes2")
        collectionReference.addSnapshotListener{ value,error->
            if(value == null || error != null){
                Toast.makeText(this,"Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        val quizRecyclerView:RecyclerView=findViewById(R.id.quizRecyclerView)
        adapter = QuizAdapter(this,quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter=adapter
    }

    private fun setUpDrawerLayout() {
        val navigationView:NavigationView = findViewById(R.id.navigationView)
        val mainDrawer :DrawerLayout = findViewById(R.id.mainDrawer)
        val appBar:androidx.appcompat.widget.Toolbar =  findViewById(R.id.appBar)
        setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawer,R.string.app_name,R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            val intent = Intent (this,ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}