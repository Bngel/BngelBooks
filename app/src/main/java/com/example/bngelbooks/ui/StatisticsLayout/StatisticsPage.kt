package com.example.bngelbooks.ui.StatisticsLayout

import android.content.Context
import android.os.Bundle
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
import java.util.*
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
        val salary_sum = OrderSum("工资",0.0)
        val partjob_sum = OrderSum("兼职",0.0)
        val redlope_sum = OrderSum("红包",0.0)

        fun get_sum_cost_list(): List<OrderSum> = listOf(
            eat_sum, traffic_sum, clothes_sum, daily_sum, medical_sum, study_sum, play_sum,
            others_sum
        )

        fun get_sum_income_list(): List<OrderSum> = listOf(
            salary_sum, partjob_sum, redlope_sum
        )

        fun init_sum_list() {
            eat_sum.Value= 0.0
            traffic_sum.Value= 0.0
            clothes_sum.Value= 0.0
            daily_sum.Value= 0.0
            medical_sum.Value= 0.0
            study_sum.Value= 0.0
            play_sum.Value= 0.0
            others_sum.Value= 0.0
            salary_sum.Value= 0.0
            partjob_sum.Value= 0.0
            redlope_sum.Value= 0.0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WidgetSetting.chart_loading.observe(this, Observer {
            if (it) {
                init_costChart()
                init_incomeChart()
            }
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
        init_costChart()
        init_incomeChart()
    }

    private fun init_data() {
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()
        orders = orderDao.loadOrdersByMonth(year,month).asReversed()
        init_sum_list()
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
            else {
                when (order.TypeName) {
                    "工资" -> salary_sum.Value += order.Value
                    "兼职" -> partjob_sum.Value += order.Value
                    "红包" -> redlope_sum.Value += order.Value
                }
            }
        }
    }

    private fun init_costChart() {
        thread {
            roseChart_Cost.setLoading(true) //是否正在加载，数据加载完毕后置为false
            init_data()
            roseChart_Cost.apply {
                setShowChartLable(true) //是否在图表上显示指示lable
                setShowChartNum(true) //是否在图表上显示指示num
                setShowNumTouched(false) //点击显示数量
                setShowRightNum(true) //右侧显示数量
                //参数1：数据对象class， 参数2：数量属性字段名称， 参数3：名称属性字段名称， 参数4：数据集合
                setData(OrderSum::class.java, "Value", "typeName", get_sum_cost_list())
                setLoading(false) //是否正在加载，数据加载完毕后置为false
            }
        }
        WidgetSetting.chart_loading.value = false
    }

    private fun init_incomeChart() {
        thread {
            roseChart_Income.setLoading(true) //是否正在加载，数据加载完毕后置为false
            init_data()
            roseChart_Income.apply {
                setShowChartLable(true) //是否在图表上显示指示lable
                setShowChartNum(true) //是否在图表上显示指示num
                setShowNumTouched(false) //点击显示数量
                setShowRightNum(true) //右侧显示数量
                //参数1：数据对象class， 参数2：数量属性字段名称， 参数3：名称属性字段名称， 参数4：数据集合
                setData(OrderSum::class.java, "Value", "typeName", get_sum_income_list())
                setLoading(false) //是否正在加载，数据加载完毕后置为false
            }
        }
        WidgetSetting.chart_loading.value = false
    }
}