package com.example.jaibkharch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransactionsTable")
data class TransactionsTable(
    var category:String,var moneyspent:Int,
    var date:String,
    var totalspendings:Int,
    var userid:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
)

