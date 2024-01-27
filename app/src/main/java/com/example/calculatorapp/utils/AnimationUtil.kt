package com.example.calculatorapp.utils

import android.animation.ObjectAnimator
import android.widget.EditText

fun getTextResizeAnimation(editTextMathExpression: EditText?, length: Int, sp: Float): ObjectAnimator {
    return when {
        length < 10 && sp != 50f ->
            ObjectAnimator.ofFloat(editTextMathExpression, "textSize", sp, 50f)

        length in 10..18 && sp != 30f ->
            ObjectAnimator.ofFloat(editTextMathExpression, "textSize", sp, 30f)

        length in 18..25 && sp != 20f ->
            ObjectAnimator.ofFloat(editTextMathExpression, "textSize", sp, 20f)

        length > 25 && sp != 15f ->
            ObjectAnimator.ofFloat(editTextMathExpression, "textSize", sp, 15f)

        else -> ObjectAnimator.ofFloat(editTextMathExpression, "textSize", sp, sp)
    }
}