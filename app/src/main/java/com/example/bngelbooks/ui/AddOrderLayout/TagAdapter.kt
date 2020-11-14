package com.example.bngelbooks.ui.AddOrderLayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.Tag

class TagAdapter(val tags: List<Tag>): RecyclerView.Adapter<TagAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tagText: TextView = view.findViewById(R.id.tagItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tag_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tags[position]
        holder.tagText.text = tag.tagText
    }

    override fun getItemCount() = tags.size
}