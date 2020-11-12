package com.example.headerpractice

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.inout_header.view.*
import kotlin.text.*

class InOutHeaderLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.inout_header,this)
        setFonts()
    }

    private fun setFonts() {
        WidgetSetting.setFonts(context, listOf(remainText,remainNumText,incomeText,incomeNumText,costText,costNumText))
    }

    fun setBalance(balance: String) {
        remainNumText.setText(balance)
    }

    fun setIncome(income: String) {
        incomeNumText.setText(income)
    }

    fun setCost(cost: String) {
        costNumText.setText(cost)
    }
}