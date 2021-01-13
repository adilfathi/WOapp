package com.example.woapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>, private val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtItem = view.txt_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.item_exercise_status, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]

        holder.txtItem.text = model.getId().toString()

        if (model.getIsSelected()) {
            holder.txtItem.background = ContextCompat.getDrawable(context, R.drawable.item_status_selected)
        } else if (model.getIsCompleted()) {
            holder.txtItem.background = ContextCompat.getDrawable(context, R.drawable.item_status_completed)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}