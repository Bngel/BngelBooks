package com.example.bngelbooks.ui.AccountDetailLayout

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.Account
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.OrderList.OrderAdapter
import com.example.bngelbooks.ui.WidgetSetting
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_account_detail.*
import kotlin.concurrent.thread

class AccountDetailActivity : AppCompatActivity() {

    lateinit var orderadapter :OrderAdapter
    lateinit var orders: List<Order>
    lateinit var orderDao: OrderDao
    lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        account = Gson().fromJson(intent.getStringExtra("account"),Account::class.java)
        WidgetSetting.setFonts(this,
            listOf(acDetailName,acDetailValue,acdetail_title))
        init_ui()
        orderDao = OrderDatabase.getDatabase(this).orderDao()
        thread {
            orders = orderDao.loadOrdersByAccount(account.acName)
        }.join()
        orderadapter = OrderAdapter(orders)
        acDetailList.adapter = orderadapter
        acDetailList.layoutManager = LinearLayoutManager(this)

        acdetail_closeBtn.setOnClickListener {
            finish()
        }

        acdetail_delete.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.z_ac_delete)
                .setTitle("提示:")
                .setMessage("是否确定删除该账户 ?")
                .setPositiveButton("确定", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        thread {
                            orderDao.deleteAccount(account)
                            orderDao.deleteOrdersByAccount(account.acName)
                        }.join()
                        WidgetSetting.account_loading.value = true
                        WidgetSetting.refresh_needed.value = true
                        WidgetSetting.chart_loading.value = true
                        finish()
                    }
                })
                .setNegativeButton("取消", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                    }
                })
                .show()
        }
    }

    private fun init_ui() {
        acDetailImg.setImageResource(account.acIcon)
        acDetailName.text = account.acName
        acDetailValue.text = String.format("%.2f",account.acValue)
    }
}