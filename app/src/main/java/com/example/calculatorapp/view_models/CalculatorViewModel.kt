package com.example.calculatorapp.view_models

import android.animation.ObjectAnimator
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(): ViewModel() {

    private val _mathExpression = MutableLiveData("")
    val mathExpression: LiveData<String>
        get() = _mathExpression

    var mathExpressionPreviousLength = 0

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

    fun updateMathExpressionPreviousLength() {
        mathExpressionPreviousLength = _mathExpression.value?.length ?: 0
    }

}