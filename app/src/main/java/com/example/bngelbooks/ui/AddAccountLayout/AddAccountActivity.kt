package com.example.bngelbooks.ui.AddAccountLayout

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
    lateinit var accounts: List<Account>
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
            val accountName = accountNameEdit.text.toString()?:""
            val accountImg = current_acIcon.value!!
            val accountValueText = accountValueEdit.text.toString()
            val accountValue = if (accountValueText == "") 0.0 else accountValueText.toDouble()
            thread {
                accounts = orderDao.loadAllAccounts()
            }.join()
            val nameEmpty = accountName == ""
            var nameExists = false
            for (ac in accounts) {
                if (ac.acName == accountName) {
                    nameExists = true
                    break
                }
            }
            if (nameExists){
                AlertDialog.Builder(this)
                    .setIcon(accountImg)
                    .setTitle("警告:")
                    .setMessage("账户名称已存在\n请重新输入")
                    .setPositiveButton("确定", object: DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    })
                    .show()
            }
            else if (nameEmpty) {
                AlertDialog.Builder(this)
                    .setIcon(accountImg)
                    .setTitle("警告:")
                    .setMessage("请输入账户名称")
                    .setPositiveButton("确定", object: DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    })
                    .show()
            }
            else {
                val account = Account(accountImg,accountName,accountValue)
                thread {
                    orderDao.insertAccount(account)
                }
                WidgetSetting.account_loading.value = true
                finish()
            }
        }

        accountcloseBtn.setOnClickListener {
            finish()
        }
    }
}