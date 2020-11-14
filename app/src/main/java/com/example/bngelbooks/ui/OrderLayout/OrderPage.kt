package com.example.bngelbooks.ui.OrderLayout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.CostItemList.CostItemAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.order_page_layout.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.bngelbooks.R

class OrderPage : Fragment() {

    lateinit var adapter: CostItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    private fun initRecyclerView() {
        val items = ArrayList<Order>()
        initDatas(items)
        adapter = CostItemAdapter(items)
        recyclerView.adapter = adapter
        val LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = LayoutManager
        refreshSet()
    }

    private fun initDatas(items: ArrayList<Order>) {
        repeat(10) {
            items.add(Order(R.drawable.eat, "吃喝", "食堂九元套餐", 9.00, Date().toString()))
        }
        repeat(10) {
            items.add(Order(R.drawable.traffic, "交通", "车费", 20000.00, Date().toString()))
        }
        repeat(10) {
            items.add(Order(R.drawable.medical, "医疗", "复查身体指标", 200.00, Date().toString()))
        }
        repeat(10) {
            items.add(Order(R.drawable.clothes, "服饰", "优衣库", 149.00, Date().toString()))
        }
    }

    private fun refreshSet() {
        refreshLayout.setRefreshHeader(ClassicsHeader(context))
        refreshLayout.setRefreshFooter(ClassicsFooter(context))
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(2000,false,false)
        }
    }
}