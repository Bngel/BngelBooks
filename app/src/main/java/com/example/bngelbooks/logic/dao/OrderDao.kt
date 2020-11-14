package com.example.bngelbooks.logic.dao

import androidx.room.*
import com.example.bngelbooks.logic.model.Order

@Dao
interface OrderDao {

    @Insert
    fun insertOrder (order: Order): Long

    @Update
    fun updateOrder (newOrder: Order)

    @Query("select * from `Order`")
    fun loadAllOrders() : List<Order>

    @Delete
    fun deleteOrder(order: Order)
}