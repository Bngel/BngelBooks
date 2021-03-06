package com.example.bngelbooks.ui.OrderList


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.logic.model.TypeIcon
import com.example.bngelbooks.logic.model.judgeIconType
import com.example.bngelbooks.ui.OrderDetailLayout.OrderDetailActivity
import com.example.bngelbooks.ui.WidgetSetting
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cost_item_layout.view.*

class OrderAdapter(val items: List<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val TypeImg: ImageView = view.findViewById(R.id.costImg)
        val TypeName: TextView = view.findViewById(R.id.TypeText)
        val TagName: TextView = view.findViewById(R.id.TagText)
        val Value: TextView = view.findViewById(R.id.ValueText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cost_item_layout,parent,false)
        WidgetSetting.setFonts(parent.context, listOf(view.TypeText, view.ValueText, view.TagText))
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val intent_order_detail = Intent(parent.context, OrderDetailActivity::class.java)
            val position = viewHolder.adapterPosition
            val order = items[position]

            intent_order_detail.putExtra("order", Gson().toJson(order))
            parent.context.startActivity(intent_order_detail)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.TypeImg.setImageResource(item.TypeImgId)
        holder.TypeName.text = item.TypeName
        holder.TagName.text = item.Tag
        val valueType = if(judgeIconType(item.TypeName)=="支出") "-" else "+"
        holder.Value.text = String.format("%s%.2f",valueType, item.Value)
    }
}