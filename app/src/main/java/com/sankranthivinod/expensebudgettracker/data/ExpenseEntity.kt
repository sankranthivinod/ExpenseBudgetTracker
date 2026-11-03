package com.sankranthivinod.expensebudgettracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val amount: Double,
    val note: String = "",
    val dateMillis: Long = System.currentTimeMillis()
)
