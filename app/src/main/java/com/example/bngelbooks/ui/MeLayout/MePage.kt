package com.example.bngelbooks.ui.MeLayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.Account
import com.example.bngelbooks.ui.AddAccountLayout.AddAccountActivity
import com.example.bngelbooks.ui.MeSettings.SettingActivity
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.me_page_layout.*
import kotlin.concurrent.thread

class MePage : Fragment() {

    lateinit var accounts: List<Account>
    lateinit var orderDao: OrderDao
    lateinit var accountAdapter: AccountAdapter
    lateinit var page_context: Context

    val ADD_ACCOUNT_INTENT = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WidgetSetting.account_loading.observe(this, Observer {
            if (it)
                init_data()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.me_page_layout, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        page_context = context
        orderDao = OrderDatabase.getDatabase(context).orderDao()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        WidgetSetting.setFonts(page_context,
            listOf(me_name,accountText))
        me_add_account.setOnClickListener {
            val intent_add_account = Intent(page_context,AddAccountActivity::class.java)
            startActivityForResult(intent_add_account,ADD_ACCOUNT_INTENT)
        }
        me_setting.setOnClickListener {
            val setting_intent = Intent(page_context,SettingActivity::class.java)
            startActivity(setting_intent)
        }
        init_data()
    }

    private fun init_data() {
        thread {
            accounts = orderDao.loadAllAccounts() ?:
                    listOf(Account(R.drawable.account_icon_blue,"TEST",200.0))
        }.join()
        accountAdapter = AccountAdapter(accounts)
        AccountAll.adapter = accountAdapter
        val LayoutManager = LinearLayoutManager(context)
        AccountAll.layoutManager = LayoutManager
        WidgetSetting.account_loading.value = false
    }
}