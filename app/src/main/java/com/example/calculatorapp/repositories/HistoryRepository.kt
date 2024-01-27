package com.example.calculatorapp.repositories

import com.example.calculatorapp.database.HistoryDao
import com.example.calculatorapp.database.entities.HistoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    fun getHistory(): Flow<List<HistoryItem>> {
        return historyDao.getHistory()
    }

    suspend fun insertHistoryItem(item: HistoryItem) {
        historyDao.insertHistoryItem(item)
    }
}