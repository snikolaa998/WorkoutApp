package com.example.workoutapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.model.ExerciseModel
import com.example.workoutapp.R
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvItem = view.tvItem
}

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>, private val context: Context): RecyclerView.Adapter<ExerciseStatusHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseStatusHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false)
        return ExerciseStatusHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExerciseStatusHolder, position: Int) {
        val item = items[position]
        with(holder) {
            tvItem.text = item.id.toString()
            if (item.isCompleted) {
                tvItem.setBackgroundResource(R.drawable.completed_exercise)
                tvItem.setTextColor(Color.WHITE)
            } else if (item.isSelected) {
                tvItem.setBackgroundResource(R.drawable.selected_exercise)
                tvItem.setTextColor(Color.BLACK)
            }
        }
    }
}