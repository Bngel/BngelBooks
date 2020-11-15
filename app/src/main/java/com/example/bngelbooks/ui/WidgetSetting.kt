package com.example.bngelbooks.ui

import android.content.Context
import android.graphics.Typeface
import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.TypeIcon
import kotlinx.android.synthetic.main.activity_add_order.*

object WidgetSetting {

    var current_icon = MutableLiveData<TypeIcon>()

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