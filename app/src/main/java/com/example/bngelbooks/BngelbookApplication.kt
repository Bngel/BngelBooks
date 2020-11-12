package com.example.bngelbooks

import android.app.Application
import android.content.Context

class BngelbookApplication : Application(){
    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}