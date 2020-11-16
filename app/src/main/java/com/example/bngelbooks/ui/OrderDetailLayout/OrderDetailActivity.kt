package com.example.bngelbooks.ui.OrderDetailLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        WidgetSetting.setFonts(this,
            listOf(detail_title,detail_value,detail_date,detail_tag,detail_text,detail_time))
    }
}