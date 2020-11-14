package com.example.bngelbooks.logic.model

import com.example.bngelbooks.R

private val typeIcon_valid = listOf(
    TypeIcon(R.drawable.eat, "吃喝"),
    TypeIcon(R.drawable.traffic, "交通"),
    TypeIcon(R.drawable.clothes, "服饰"),
    TypeIcon(R.drawable.daily, "日用品"),
    TypeIcon(R.drawable.medical, "医疗"),
    TypeIcon(R.drawable.study, "学习"),
    TypeIcon(R.drawable.play, "娱乐"),
    TypeIcon(R.drawable.others, "其他")
)

fun getTypeIcons() = typeIcon_valid