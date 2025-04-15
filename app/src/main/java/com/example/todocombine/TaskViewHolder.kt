package com.example.todocombine

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.task_title)
    val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
}
