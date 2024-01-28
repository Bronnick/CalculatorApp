package com.example.calculatorapp.utils

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

fun vibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val effect = VibrationEffect.createOneShot(50L, 10)
    vibrator.vibrate(effect)
}