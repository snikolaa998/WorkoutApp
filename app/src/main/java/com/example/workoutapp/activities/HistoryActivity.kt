package com.example.workoutapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.DatabaseOpenHelper
import com.example.workoutapp.R
import com.example.workoutapp.adapters.HistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val toolbar = toolbar_activity_history
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "HISTORY"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRecycler()
    }

    private fun setupRecycler() {
        val databaseHandler = DatabaseOpenHelper(this, null)
        val historyList = databaseHandler.getHistory()
        val adapter = HistoryAdapter(historyList, this)
        historyRecycler.layoutManager = LinearLayoutManager(this)
        historyRecycler.adapter = adapter
    }
}