package com.example.bngelbooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bngelbooks.logic.network.RecordService
import com.example.bngelbooks.ui.AddOrderLayout.AddOrderActivity
import com.example.bngelbooks.ui.MeLayout.MePage
import com.example.bngelbooks.ui.OrderLayout.OrderPage
import com.example.bngelbooks.ui.StatisticsLayout.StatisticsPage
import com.rbddevs.splashy.Splashy
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {

    private lateinit var fragments: ArrayList<Fragment>

    private val ADD_ORDER_INTENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSplash()
        initData()
        initBNVE()
        initEvent()
        initService()
    }

    private fun initEvent() {
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                val item = bnve.menu.getItem(1)
                if (1 == position) {
                    item.icon = null
                    item.title = ""
                    fab.show()
                } else {
                    fab.hide()
                    item.setIcon(R.drawable.foot_list)
                    item.title = "账目"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        fab.setOnClickListener {
            val intent_add_order = Intent(this, AddOrderActivity::class.java)
            startActivityForResult(intent_add_order, ADD_ORDER_INTENT)
        }
    }

    private fun initData() {
        // 将需要切换的 Fragment 进行传入
        fragments = ArrayList(3)
        val statisticsFragment = StatisticsPage()
        val orderFragment = OrderPage()
        val meFragment = MePage()
        fragments.let {
            it.add(statisticsFragment)
            it.add(orderFragment)
            it.add(meFragment)
        }
    }

    private fun initBNVE() {
        val adapter = VpAdapter(supportFragmentManager, fragments)
        vp.setAdapter(adapter)
        bnve.setupWithViewPager(vp)
        bnve.currentItem = 1
    }

    private class VpAdapter(fm: FragmentManager, data: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
        private val data: ArrayList<Fragment>
        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Fragment {
            return data[position]
        }

        init {
            this.data = data
        }
    }

    private fun initService() {
        val isSwitched = getSharedPreferences("SwitchStatus", Context.MODE_PRIVATE)
            .getBoolean("record",false)
        val service_Intent = Intent(this,RecordService::class.java)
        if (isSwitched)
            startService(service_Intent)
    }

    private fun initSplash() {
        Splashy(this)
            .setLogo(R.drawable.splash_icon)
            .setTitle("Bngel")
            .setTitleColor("#FFFFFF")
            .setSubTitle("Bngel's Cost Record")
            .setProgressColor(R.color.white)
            .setBackgroundResource(R.drawable.splash_bg)
            .setFullScreen(true)
            .setTime(2000)
            .show()
    }
}