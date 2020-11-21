package com.example.bngelbooks.ui.AddAccountLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.Account
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_add_account.*
import kotlin.concurrent.thread

class AddAccountActivity : AppCompatActivity() {

    lateinit var orderDao: OrderDao
    val current_acIcon = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        orderDao = OrderDatabase.getDatabase(this).orderDao()
        current_acIcon.value = R.drawable.account_icon_blue
        WidgetSetting.setFonts(this,
            listOf(addAccountTitle,accountName,accountValue)
        )

        account_blue_img.setOnClickListener {
            current_acIcon.value = R.drawable.account_icon_blue
        }

        account_orange_img.setOnClickListener {
            current_acIcon.value = R.drawable.account_icon_orange
        }

        account_green_img.setOnClickListener {
            current_acIcon.value = R.drawable.account_icon_green
        }

        account_pink_img.setOnClickListener {
            current_acIcon.value = R.drawable.account_icon_pink
        }

        current_acIcon.observe(this, Observer {
            selectedAccountImg.setImageResource(current_acIcon.value!!)
        })

        acokBtn.setOnClickListener {
            val account = Account(current_acIcon.value!!,accountNameEdit.text.toString(),
                accountValueEdit.text.toString().toDouble())
            thread {
                orderDao.insertAccount(account)
            }
            WidgetSetting.account_loading.value = true
            finish()
        }

        accountcloseBtn.setOnClickListener {
            finish()
        }
    }
}