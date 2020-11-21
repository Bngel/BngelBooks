package com.example.bngelbooks.ui.OrderLayout
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.OrderList.OrderAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.order_page_layout.*
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.judgeIconType
import com.example.bngelbooks.ui.OrderSearchLayout.OrderSearchActivity
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.book_selected_layout.*
import kotlinx.android.synthetic.main.inout_header.*
import kotlin.concurrent.thread

class OrderPage() : Fragment() {

    lateinit var adapter: OrderAdapter
    lateinit var orderDao: OrderDao
    lateinit var orders : List<Order>
    var sum_of_cost = 0.0
    var sum_of_income = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WidgetSetting.current_income.observe(this, Observer {
            incomeNumText.text = it.toString()
            remainNumText.text = (incomeNumText.text.toString().toDouble() - costNumText.text.toString().toDouble()).toString()
        })
        WidgetSetting.current_cost.observe(this, Observer {
            costNumText.text = it.toString()
            remainNumText.text = (incomeNumText.text.toString().toDouble() - costNumText.text.toString().toDouble()).toString()
        })
        WidgetSetting.refresh_needed.observe(this, Observer {
            if (it)
                initRecyclerView()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_page_layout, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        searchBtn.setOnClickListener {
            val intent = Intent(context,OrderSearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderDao = OrderDatabase.getDatabase(context).orderDao()
    }

    private fun initOrdersList() {
        thread {
            orders = orderDao.loadAllOrders().asReversed()
        }.join()
    }

    private fun initRecyclerView() {
        initOrdersList()
        sum_of_cost = 0.0
        sum_of_income = 0.0
        for (order in orders.asReversed()) {
            when (judgeIconType(order.TypeName)) {
                "收入" ->
                    sum_of_income += order.Value
                "支出" ->
                    sum_of_cost += order.Value
            }
        }
        WidgetSetting.current_cost.value = sum_of_cost
        WidgetSetting.current_income.value = sum_of_income
        adapter = OrderAdapter(orders)
        recyclerView.adapter = adapter
        val LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = LayoutManager
        refreshSet()
        WidgetSetting.refresh_needed.value = false
    }

    private fun refreshSet() {
        refreshLayout.setRefreshHeader(ClassicsHeader(context))
        refreshLayout.setRefreshFooter(ClassicsFooter(context))
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            initRecyclerView()
            refreshLayout.finishRefresh()
        }
    }
}