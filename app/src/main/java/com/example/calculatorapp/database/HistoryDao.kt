package com.example.calculatorapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.calculatorapp.database.entities.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryItem>>

    @Insert
    suspend fun insertHistoryItem(item: HistoryItem)
}