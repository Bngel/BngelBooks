package com.example.bngelbooks.ui.InOutHeader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.bngelbooks.R

class BookSelectedHeader(context: Context, attrs: AttributeSet) : RelativeLayout(context,attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.book_selected_layout, this)
    }
}