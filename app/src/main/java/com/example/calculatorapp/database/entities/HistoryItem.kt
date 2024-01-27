package com.example.calculatorapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val mathExpression: String = "",

    val result: String = ""
)
