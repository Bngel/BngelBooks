package com.example.bngelbooks.ui.StaticsHeader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.statistics_header_layout.view.*

class StaticsHeaderLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context,attrs){
    init {
        LayoutInflater.from(context).inflate(R.layout.statistics_header_layout,this)
        WidgetSetting.setFont(context,statisticsText)
    }
}