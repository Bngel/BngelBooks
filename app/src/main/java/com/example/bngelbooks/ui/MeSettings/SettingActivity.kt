package com.example.bngelbooks.ui.MeSettings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.WidgetSetting
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initSwitchButton()
        WidgetSetting.setFont(this,msgRecordText)
        setting_closeBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun initSwitchButton() {
        msgRecordSwitch.isChecked = getSharedPreferences("SwitchStatus", Context.MODE_PRIVATE)
            .getBoolean("record",false)
        msgRecordSwitch.toggle() //switch state
        msgRecordSwitch.toggle(true) //switch without animation
        msgRecordSwitch.setShadowEffect(true) //disable shadow effect
        msgRecordSwitch.isEnabled = true //disable button
        msgRecordSwitch.setEnableEffect(true) //disable the switch animation
        msgRecordSwitch.setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener { view, isChecked ->
            getSharedPreferences("SwitchStatus", Context.MODE_PRIVATE).edit().apply {
                putBoolean("record",isChecked)
                apply()
            }
        })
    }

}