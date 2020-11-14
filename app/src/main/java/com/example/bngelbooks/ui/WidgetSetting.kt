package com.example.bngelbooks.ui

import android.content.Context
import android.graphics.Typeface
import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import com.example.bngelbooks.logic.model.TypeIcon

object WidgetSetting {

    fun setFont(context: Context, view: TextView) {
        val mtypeface = Typeface.createFromAsset(context.assets,"font/HGHP_CNKI.TTF")
        view.setTypeface(mtypeface)
    }

    fun setFonts(context: Context, views: List<TextView>) {
        val mtypeface = Typeface.createFromAsset(context.assets,"font/HGHP_CNKI.TTF")
        for (view in views) {
            view.setTypeface(mtypeface)
        }
    }
}