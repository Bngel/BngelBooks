package com.example.bngelbooks.ui.MeLayout

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.Account
import com.example.bngelbooks.ui.AccountDetailLayout.AccountDetailActivity
import com.google.gson.Gson

class AccountAdapter (val accounts: List<Account>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val accountImg: ImageView = view.findViewById(R.id.account_img)
        val accountText: TextView = view.findViewById(R.id.account_text)
        val accountValue: TextView = view.findViewById(R.id.account_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_item_layout,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val intent_account_detail = Intent(parent.context,AccountDetailActivity::class.java)
            val position = viewHolder.adapterPosition
            val account = accounts[position]

            intent_account_detail.putExtra("account", Gson().toJson(account))
            parent.context.startActivity(intent_account_detail)
        }
        return viewHolder
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.accountImg.setImageResource(account.acIcon)
        holder.accountText.text = account.acName
        holder.accountValue.text = String.format("%.2f",account.acValue)
    }


}