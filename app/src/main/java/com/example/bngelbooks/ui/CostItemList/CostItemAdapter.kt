package com.example.bngelbooks.ui.CostItemList


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.Cost_Item
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.cost_item_layout.view.*

class CostItemAdapter(val items: List<Cost_Item>) : RecyclerView.Adapter<CostItemAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val TypeImg: ImageView = view.findViewById(R.id.EatImg)
        val TypeName: TextView = view.findViewById(R.id.TypeText)
        val TagName: TextView = view.findViewById(R.id.TagText)
        val Value: TextView = view.findViewById(R.id.ValueText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cost_item_layout,parent,false)
        WidgetSetting.setFonts(parent.context, listOf(view.TypeText, view.ValueText, view.TagText))
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.TypeImg.setImageResource(item.TypeImgId)
        holder.TypeName.text = item.TypeName
        holder.TagName.text = item.Tag
        holder.Value.text = String.format("%.2f", item.Value)
    }
}