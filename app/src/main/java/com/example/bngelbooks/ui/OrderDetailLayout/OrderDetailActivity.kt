package com.example.bngelbooks.ui.OrderDetailLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.WidgetSetting
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlin.concurrent.thread

class OrderDetailActivity : AppCompatActivity() {

    lateinit var orderDao: OrderDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        WidgetSetting.setFonts(this,
            listOf(detail_title,detail_value,detail_date,detail_tag,detail_text,detail_time))
        val order = Gson().fromJson(intent.getStringExtra("order"),Order::class.java)
        detail_img.setImageResource(order.TypeImgId)
        detail_text.text = order.TypeName
        detail_tag.text = order.Tag
        detail_value.text = order.Value.toString()
        val datetime = order.Time.split(' ')
        val time = datetime[1].split(':')
        detail_date.text = datetime[0]
        detail_time.text = String.format("%s:%s",time[0],time[1])

        orderDao = OrderDatabase.getDatabase(this).orderDao()

        detail_closeBtn.setOnClickListener {
            finish()
        }

        detail_delete.setOnClickListener {
            thread {
                orderDao.deleteOrder(order)
            }
            WidgetSetting.refresh_needed.value = true
            WidgetSetting.chart_loading.value = true
            WidgetSetting.account_loading.value = true
            finish()
        }
    }
}