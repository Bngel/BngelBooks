package com.example.bngelbooks.logic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bngelbooks.logic.dao.OrderDao
import com.example.bngelbooks.logic.model.Order
import com.example.bngelbooks.ui.AddOrderLayout.AddOrderActivity
import com.example.bngelbooks.ui.OrderLayout.OrderPage

@Database (version = 1, entities = [Order::class])
abstract class OrderDatabase : RoomDatabase(){

    abstract fun orderDao(): OrderDao

    companion object {

        private var instance: OrderDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): OrderDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
            OrderDatabase::class.java, "order_database")
                .build()
                .apply {
                    instance = this
                }
        }
    }
}