package com.example.workoutapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.DatabaseOpenHelper
import com.example.workoutapp.R
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        addDateToDatabase()
        val toolbar = toolbar_finish_activity
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        btnFinish.setOnClickListener {
            finish()
        }
    }
    private fun addDateToDatabase() {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
//        Log.d("DATE", dateTime.toString())
        val simpleDateFormat = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
        val date = simpleDateFormat.format(dateTime)
//        Log.d("DATE", date.toString())

        val databaseHandler = DatabaseOpenHelper(this, null)
        databaseHandler.addDate(date)
    }
}