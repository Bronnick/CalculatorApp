package com.example.calculatorapp.view_models

import android.animation.ObjectAnimator
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorapp.classes.evaluate
import com.example.calculatorapp.database.entities.HistoryItem
import com.example.calculatorapp.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
): ViewModel() {

    private val _mathExpression = MutableLiveData("")
    val mathExpression: LiveData<String>
        get() = _mathExpression

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    private val _equalsPressedFlag = MutableLiveData(false)
    val equalsPressedFlag: LiveData<Boolean>
        get() = _equalsPressedFlag

    var mathExpressionPreviousLength = 0


    val history: Flow<List<HistoryItem>> =
        historyRepository.getHistory()


    init {
        Log.d("myLogs", "init")
    }

    fun updateMathExpression(s: String) {
        _mathExpression.value += s
        Log.d("myLogs", "beforeSize: $mathExpressionPreviousLength")
    }

    fun removeLastSymbol() {
        _mathExpression.value = _mathExpression.value?.dropLast(1)
    }

    fun clear() {
        _mathExpression.value = ""
    }

    fun saveHistoryItem(item: HistoryItem) {
        viewModelScope.launch {
            historyRepository.insertHistoryItem(item)
        }
    }

    fun switchMathExpressionWithResult() {
        if(_result.value?.isEmpty() == true) return
        _mathExpression.value = _result.value
        _result.value = ""
        _equalsPressedFlag.value = !_equalsPressedFlag.value!!
    }

    fun updateMathExpressionPreviousLength() {
        mathExpressionPreviousLength = _mathExpression.value?.length ?: 0
    }

    fun evaluateExpression(s: String) {
        _result.value =
            try {
                evaluate(s).toString()
            } catch(e: Exception) {
                ""
            }
    }

}