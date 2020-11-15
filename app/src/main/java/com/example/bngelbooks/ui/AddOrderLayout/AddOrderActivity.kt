package com.example.bngelbooks.ui.AddOrderLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.*
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlin.collections.ArrayList

class AddOrderActivity : AppCompatActivity() {
    lateinit var iconAdapter: IconAdapter
    lateinit var tagAdapter: TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        SmallIconImg.setImageResource(R.drawable.eat)
        SmallIconType.text = "吃喝"

        val icons = ArrayList<TypeIcon>()
        icons.addAll(getTypeIcons())
        iconAdapter = IconAdapter(icons)
        iconsView.adapter = iconAdapter
        val LayoutManager = LinearLayoutManager(this)
        iconsView.layoutManager = LayoutManager

        tagAdapter = TagAdapter(get_eatTags())
        tagsView.adapter = tagAdapter
        val LayoutManager2 = LinearLayoutManager(this)
        tagsView.layoutManager = LayoutManager2

        closeBtn.setOnClickListener {
            finish()
        }

        WidgetSetting.current_icon.observe(this, Observer { new_Icon ->
            SmallIconImg.setImageResource(new_Icon.iconImg)
            SmallIconType.text = new_Icon.typeName
            tagsView.adapter = when (new_Icon.typeName) {
                "吃喝" ->  TagAdapter(get_eatTags())
                "交通" -> TagAdapter(get_trafficTags())
                "服饰" -> TagAdapter(get_clothesTags())
                "日用品" -> TagAdapter(get_dailyTags())
                "医疗" -> TagAdapter(get_medicalTags())
                "学习" -> TagAdapter(get_studyTags())
                "娱乐" -> TagAdapter(get_playTags())
                "其他" -> TagAdapter(get_othersTags())
                else -> TagAdapter(listOf())
            }
        })
    }
}