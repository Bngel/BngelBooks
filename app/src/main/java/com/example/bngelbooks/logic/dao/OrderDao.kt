package com.example.bngelbooks.logic.dao

import androidx.room.*
import com.example.bngelbooks.logic.model.Account
import com.example.bngelbooks.logic.model.Order

@Dao
interface OrderDao {

    @Insert
    fun insertOrder (order: Order): Long

    @Update
    fun updateOrder (newOrder: Order)

    @Query("select * from Orders")
    fun loadAllOrders() : List<Order>

    @Delete
    fun deleteOrder(order: Order)

    @Query("delete from Orders")
    fun deleteAllOrders()

    @Insert
    fun insertAccount (account: Account): Long

    @Update
    fun updateAccount (newAccount: Account)

    @Query("select * from Accounts")
    fun loadAllAccounts(): List<Account>

    @Delete
    fun deleteAccount(account: Account)

    @Query("select * from Accounts where acName == :name")
    fun loadAccountsByName(name: String): List<Account>

    @Query("select * from Orders where Account == :account")
    fun loadOrdersByAccount(account: String): List<Order>

    @Query("delete from Orders where Account == :account")
    fun deleteOrdersByAccount(account: String)
}