package com.example.bngelbooks.ui.AddOrderLayout

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.*
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_add_order.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer as Observer

class AddOrderActivity : AppCompatActivity() {
    lateinit var iconAdapter: IconAdapter
    lateinit var tagAdapter: TagAdapter
    val INCOME = 1
    val COST = 0
    var in_out = COST

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        SmallIconImg.setImageResource(R.drawable.eat)
        SmallIconType.text = "吃喝"

        val icons = ArrayList<TypeIcon>()
        icons.addAll(getCostTypeIcons())
        iconAdapter = IconAdapter(icons)
        iconsView.adapter = iconAdapter
        val IconLayoutManager = LinearLayoutManager(this)
        iconsView.layoutManager = IconLayoutManager

        tagAdapter = TagAdapter(get_eatTags())
        tagsView.adapter = tagAdapter
        val TagLayoutManager = LinearLayoutManager(this)
        tagsView.layoutManager = TagLayoutManager

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
                "工资" -> TagAdapter(get_salaryTags())
                "兼职" -> TagAdapter(get_partjobTags())
                "红包" -> TagAdapter(get_redlopeTags())
                else -> TagAdapter(listOf())
            }
        })

        WidgetSetting.current_tag.observe(this, Observer { new_Tag ->
            if (new_Tag != tagAdapter.prev_tag){
                val prev_tag = TagLayoutManager.findViewByPosition(tagAdapter.prev_tag)
                prev_tag?.setBackgroundColor(android.R.color.transparent)
                tagAdapter.prev_tag = new_Tag
                val tag = TagLayoutManager.findViewByPosition(new_Tag)
                tag?.setBackgroundColor(R.color.tagSelected)
            }
            else {
                val tag = TagLayoutManager.findViewByPosition(new_Tag)
                tag?.setBackgroundColor(android.R.color.transparent)
                tagAdapter.prev_tag = -1
            }
        })

        incomeBtn.setOnClickListener {
            iconsView.adapter = IconAdapter(getIncomeTypeIcons())
            tagsView.adapter = TagAdapter(get_salaryTags())
            SmallIconImg.setImageResource(R.drawable.salary)
            SmallIconType.text = "工资"
            in_out = INCOME
        }

        outcomeBtn.setOnClickListener {
            iconsView.adapter = IconAdapter(getCostTypeIcons())
            tagsView.adapter = TagAdapter(get_eatTags())
            SmallIconImg.setImageResource(R.drawable.eat)
            SmallIconType.text = "吃喝"
            in_out = COST
        }

        okBtn.setOnClickListener {
            val icon = iconAdapter.current_icon
            val tag = tagAdapter.final_tag
            var value = orderEdit.text.toString().toDouble()
            if (in_out == COST) value = -value
            val order = Order(icon.iconImg, icon.typeName,
                tag, value,
                Date().toString()
            )
        }
    }
}