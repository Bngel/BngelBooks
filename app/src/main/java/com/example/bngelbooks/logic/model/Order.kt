package com.example.bngelbooks.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "Orders")
data class Order(val TypeImgId: Int, val TypeName: String, val Tag: String, val Value: Double, val Time: String, val Account: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}