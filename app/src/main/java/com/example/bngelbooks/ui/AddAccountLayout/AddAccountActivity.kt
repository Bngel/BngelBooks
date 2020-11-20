package com.example.bngelbooks.ui.AddAccountLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bngelbooks.R
import com.example.bngelbooks.ui.WidgetSetting
import kotlinx.android.synthetic.main.activity_add_account.*

class AddAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        WidgetSetting.setFonts(this,
            listOf(addAccountTitle,accountName,accountValue)
        )
    }
}