package com.example.bngelbooks.ui.AddOrderLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.model.TypeIcon
import com.example.bngelbooks.logic.model.getTypeIcons
import com.example.bngelbooks.logic.model.get_eatTags
import kotlinx.android.synthetic.main.activity_add_order.*

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
    }
}