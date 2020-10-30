package com.example.workoutapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.model.HistoryModel
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryHolder(view: View): RecyclerView.ViewHolder(view) {
    val historyItem = view.historyTextView
    val historyItemId = view.idTextView
}

class HistoryAdapter(private val historyList: ArrayList<HistoryModel>, private val context: Context): RecyclerView.Adapter<HistoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
        return HistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val item = historyList[position]
        with(holder) {
            historyItem.text = item.date
            historyItemId.text = item.id.toString()
        }
    }

}