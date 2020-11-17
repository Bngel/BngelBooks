package com.example.bngelbooks.ui.StatisticsLayout

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.bngelbooks.R
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.database.OrderDatabase
import com.example.bngelbooks.logic.model.*
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.statistics_page_layout.*
import kotlin.concurrent.thread


class StatisticsPage: Fragment() {

    lateinit var orderDao: OrderDao
    lateinit var orders : List<Order>

    class OrderSum(val typeName: String, var Value: Double)

    companion object {
        val eat_sum = OrderSum("吃喝",0.0)
        val traffic_sum = OrderSum("交通",0.0)
        val clothes_sum = OrderSum( "服饰",0.0)
        val daily_sum = OrderSum( "日用品",0.0)
        val medical_sum = OrderSum( "医疗",0.0)
        val study_sum = OrderSum( "学习",0.0)
        val play_sum = OrderSum( "娱乐",0.0)
        val others_sum = OrderSum( "其他",0.0)

        fun get_sum_list(): List<OrderSum> = listOf(
            eat_sum, traffic_sum, clothes_sum, daily_sum, medical_sum, study_sum, play_sum,
            others_sum
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WidgetSetting.chart_loading.observe(this, Observer {
            if (it)
                initChart()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.statistics_page_layout, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderDao = OrderDatabase.getDatabase(context).orderDao()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initChart()
    }

    private fun initChart() {
        thread {
            roseChartSmall.setLoading(true) //是否正在加载，数据加载完毕后置为false
            orders = orderDao.loadAllOrders()
            for (order in orders) {
                if (judgeIconType(order.TypeName) == "支出") {
                    when (order.TypeName) {
                        "吃喝" -> eat_sum.Value += order.Value
                        "交通" -> traffic_sum.Value += order.Value
                        "服饰" -> clothes_sum.Value += order.Value
                        "日用品" -> daily_sum.Value += order.Value
                        "医疗" -> medical_sum.Value += order.Value
                        "学习" -> study_sum.Value += order.Value
                        "娱乐" -> play_sum.Value += order.Value
                        "其他" -> others_sum.Value += order.Value
                    }
                }
            }

            roseChartSmall.setShowChartLable(true) //是否在图表上显示指示lable
            roseChartSmall.setShowChartNum(true) //是否在图表上显示指示num
            roseChartSmall.setShowNumTouched(false) //点击显示数量
            roseChartSmall.setShowRightNum(true) //右侧显示数量

            //参数1：数据对象class， 参数2：数量属性字段名称， 参数3：名称属性字段名称， 参数4：数据集合
            roseChartSmall.setData(OrderSum::class.java, "Value", "typeName", get_sum_list())
            Log.d("BED","loading")
            roseChartSmall.setLoading(false) //是否正在加载，数据加载完毕后置为false
        }
        WidgetSetting.chart_loading.value = false
    }
}