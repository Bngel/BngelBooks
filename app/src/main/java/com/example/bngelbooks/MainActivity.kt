package com.example.bngelbooks

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bngelbooks.ui.MeLayout.MePage
import com.example.bngelbooks.ui.OrderLayout.OrderPage
import com.example.bngelbooks.ui.StatisticsLayout.StatisticsPage
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var fragments: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initBNVE()
        initEvent()
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
            Toast.makeText(this,"TEST", Toast.LENGTH_SHORT).show()
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
}