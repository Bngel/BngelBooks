package com.example.bngelbooks.ui.OrderSearchLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.OrderList.OrderAdapter
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_order_search.*
import kotlin.concurrent.thread

class OrderSearchActivity : AppCompatActivity() {
    lateinit var orderDao: OrderDao
    lateinit var searchAdaper: OrderAdapter
    lateinit var orders : List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_search)
        orderDao = OrderDatabase.getDatabase(this).orderDao()
        searchEnterBtn.setOnClickListener {
            loadOrders()
        }
        search_closeBtn.setOnClickListener {
            finish()
        }
        WidgetSetting.refresh_needed.observe(this, Observer {
            loadOrders()
        })
    }

    private fun loadOrders() {
        thread {
            orders = orderDao.loadOrdersByTag(searchEdit.text.toString())
        }.join()
        searchAdaper = OrderAdapter(orders)
        ordersearchList.adapter = searchAdaper
        ordersearchList.layoutManager = LinearLayoutManager(this)
    }
}