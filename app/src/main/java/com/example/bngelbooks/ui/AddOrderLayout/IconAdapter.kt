package com.example.bngelbooks.ui.AddOrderLayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.TypeIcon
import com.example.bngelbooks.ui.WidgetSetting

class IconAdapter(val icons: List<TypeIcon>): RecyclerView.Adapter<IconAdapter.ViewHolder>(){

    var current_icon = icons[0]

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val IconImg: ImageView = view.findViewById(R.id.iconImg)
        val IconName: TextView = view.findViewById(R.id.iconType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.icon_layout, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            WidgetSetting.current_icon.value = icons[position]
            current_icon = icons[position]
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val icon = icons[position]
        holder.IconImg.setImageResource(icon.iconImg)
        holder.IconName.text = icon.typeName
    }

    override fun getItemCount() = icons.size

}