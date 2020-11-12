package com.example.bngelbooks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bngelbooks.logic.model.Cost_Item
import com.example.bngelbooks.ui.CostItemList.CostItemAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var adapter: CostItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val items = ArrayList<Cost_Item>()
        initDatas(items)
        adapter = CostItemAdapter(items)
        recyclerView.adapter = adapter
        val LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LayoutManager
        refreshSet()
    }

    private fun initDatas(items: ArrayList<Cost_Item>) {
        repeat(10) {
            items.add(Cost_Item(R.drawable.eat, "吃喝", "食堂九元套餐", 9.00, Date()))
        }
        repeat(10) {
            items.add(Cost_Item(R.drawable.traffic, "交通", "车费", 20000.00, Date()))
        }
        repeat(10) {
            items.add(Cost_Item(R.drawable.medical, "医疗", "复查身体指标", 200.00, Date()))
        }
        repeat(10) {
            items.add(Cost_Item(R.drawable.clothes, "服饰", "优衣库", 149.00, Date()))
        }
    }

    private fun refreshSet() {
        refreshLayout.setRefreshHeader(ClassicsHeader(this))
        refreshLayout.setRefreshFooter(ClassicsFooter(this))
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(2000 /*,false*/) //传入false表示刷新失败
        }
    }
}