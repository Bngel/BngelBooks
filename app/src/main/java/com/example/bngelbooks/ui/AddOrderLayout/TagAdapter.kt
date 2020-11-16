package com.example.bngelbooks.ui.AddOrderLayout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.Tag
import com.example.bngelbooks.ui.WidgetSetting

class TagAdapter(val tags: List<Tag>): RecyclerView.Adapter<TagAdapter.ViewHolder>(){

    var final_tag = ""

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tagText: TextView = view.findViewById(R.id.tagItem)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tag_layout, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            final_tag = tags[position].tagText
            WidgetSetting.current_tag.value = position
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tags[position]
        holder.tagText.text = tag.tagText
    }

    override fun getItemCount() = tags.size
}