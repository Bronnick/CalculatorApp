package com.example.calculatorapp.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.calculatorapp.R
import com.example.calculatorapp.databinding.TextPanelBinding
import com.example.calculatorapp.view_models.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MathExpressionFragment : Fragment(R.layout.text_panel) {
    private var binding: TextPanelBinding? = null

    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TextPanelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mathExpression.observe(activity as LifecycleOwner) {
            val editTextMathExpression = binding?.editTextMathExpression
            val editTextResult = binding?.editTextResult
            val length = it.length
            val mathExpressionPreviousLength = viewModel.mathExpressionPreviousLength
            editTextMathExpression?.setText(it)
            editTextResult?.setText(
                try {
                    Log.d("myLogs", viewModel.evaluateExpression(it).toString())
                    viewModel.evaluateExpression(it).toString()
                } catch (e: Exception) {
                    Log.d("myLogs", e.message ?: "")
                    ""
                }
            )
            val animation = when {
                mathExpressionPreviousLength == 13 && length == 14 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 50f, 30f)
                mathExpressionPreviousLength == 20 && length == 21 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 30f, 20f)
                mathExpressionPreviousLength == 25 && length == mathExpressionPreviousLength + 1 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 20f, 15f)
                mathExpressionPreviousLength == 21 && length == mathExpressionPreviousLength - 1 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 20f, 30f)
                mathExpressionPreviousLength == 26 && length == mathExpressionPreviousLength - 1 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 15f, 20f)
                mathExpressionPreviousLength == 14 && length == mathExpressionPreviousLength - 1 ->
                    ObjectAnimator.ofFloat(editTextMathExpression, "textSize", 30f, 50f)
                else -> {
                    viewModel.updateMathExpressionPreviousLength()
                    return@observe
                }
            }
            animation.duration = 300L
            animation.interpolator = AccelerateInterpolator()
            animation.start()
            viewModel.updateMathExpressionPreviousLength()
        }
    }
}