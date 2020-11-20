package com.example.bngelbooks.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Accounts")
data class Account(val acIcon: Int, val acName: String, val acValue: Double){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}