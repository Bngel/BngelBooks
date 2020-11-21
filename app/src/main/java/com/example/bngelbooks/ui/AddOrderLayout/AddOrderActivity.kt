package com.example.bngelbooks.ui.AddOrderLayout

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.*
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_add_order.*
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import androidx.lifecycle.Observer as Observer

class AddOrderActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var iconAdapter: IconAdapter
    lateinit var tagAdapter: TagAdapter
    lateinit var orderDao: OrderDao
    val accounts = LinkedList<String>()
    val INCOME = 1
    val COST = 0
    var in_out = COST

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        orderDao = OrderDatabase.getDatabase(this).orderDao()

        SmallIconImg.setImageResource(R.drawable.eat)
        WidgetSetting.setFont(this, SmallIconType)
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
            tagAdapter = when (new_Icon.typeName) {
                "吃喝" -> TagAdapter(get_eatTags())
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
            tagsView.adapter = tagAdapter
            selectedTag.text = "请选择标签"
            tagAdapter.final_tag = ""
        })

        WidgetSetting.current_tag.observe(this, Observer { new_Tag ->
            selectedTag.text = tagAdapter.final_tag
            tagsView.adapter = tagAdapter
        })

        incomeBtn.setOnClickListener {
            iconAdapter = IconAdapter(getIncomeTypeIcons())
            iconsView.adapter = iconAdapter
            tagAdapter = TagAdapter(get_salaryTags())
            tagsView.adapter = tagAdapter
            SmallIconImg.setImageResource(R.drawable.salary)
            SmallIconType.text = "工资"
            in_out = INCOME
        }

        outcomeBtn.setOnClickListener {
            iconAdapter = IconAdapter(getCostTypeIcons())
            iconsView.adapter = iconAdapter
            tagAdapter = TagAdapter(get_eatTags())
            tagsView.adapter = tagAdapter
            SmallIconImg.setImageResource(R.drawable.eat)
            SmallIconType.text = "吃喝"
            in_out = COST
        }

        okBtn.setOnClickListener {
            val icon = iconAdapter.current_icon
            val tag = tagAdapter.final_tag
            val value_text = orderEdit.text.toString()
            var value: Double = if (value_text.isEmpty()) 0.0 else value_text.toDouble()
            val account = accountList.selectedItem.toString()
            // if (in_out == COST) value = -value
            val order = Order(
                icon.iconImg, icon.typeName,
                tag, value,
                Timestamp(Date().time).toString(),
                if (account != "不选择账户") account else ""
            )

            thread {
                // orderDao.deleteAllOrders()
                order.id = orderDao.insertOrder(order)
                if (order.Account != ""){
                    var update_account = orderDao.loadAccountsByName(order.Account)[0]
                    update_account.acValue += if (in_out == COST) -order.Value else order.Value
                    orderDao.updateAccount(update_account)
                }
            }.join()

            WidgetSetting.refresh_needed.value = true
            WidgetSetting.chart_loading.value = true
            WidgetSetting.account_loading.value = true
            finish()
        }

        DIYtagText.setOnClickListener {
            if (DIYtagEdit.isVisible) {
                DIYtagEdit.setText("")
                DIYtagEdit.visibility = View.GONE
            }
            else
                DIYtagEdit.visibility = View.VISIBLE
        }

        DIYtagEdit.doAfterTextChanged {
            selectedTag.text = DIYtagEdit.text
            tagAdapter.final_tag = selectedTag.text.toString()
        }

        thread {
            val accounts_copy = orderDao.loadAllAccounts()
            for (account in accounts_copy)
                accounts.add(account.acName)
            accounts.addFirst("不选择账户")
        }.join()

        val spinnerAdapter = ArrayAdapter(
            this,R.layout.spinner_layout, accounts
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(accountList) {
            adapter = spinnerAdapter
            onItemSelectedListener = this@AddOrderActivity
            gravity = Gravity.CENTER
            prompt = "账户选择"
        }
    }

    override fun onResume() {
        super.onResume()
        selectedTag.text = "请选择标签"
        tagAdapter.final_tag = ""
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}