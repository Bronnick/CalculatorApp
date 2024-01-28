package com.example.calculatorapp.ui.fragments

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.calculatorapp.R
import com.example.calculatorapp.database.entities.HistoryItem
import com.example.calculatorapp.databinding.ButtonPanelBinding
import com.example.calculatorapp.utils.vibrate
import com.example.calculatorapp.view_models.CalculatorViewModel

class ButtonFragment : Fragment(R.layout.button_panel){
    private var binding: ButtonPanelBinding? = null

    private val viewModel: CalculatorViewModel by activityViewModels()

    private var animSet: AnimatorSet? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ButtonPanelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.root?.children?.let {
            it.forEach { view ->
                view.setOnClickListener { currentView ->
                    currentView as Button
                    vibrate(requireActivity())
                    when (currentView.id) {
                        R.id.buttonClear -> viewModel.clear()
                        R.id.buttonEquals -> {
                            viewModel.saveHistoryItem(
                                HistoryItem(
                                    mathExpression = viewModel.mathExpression.value.toString(),
                                    result = viewModel.result.value.toString()
                                )
                            )
                            viewModel.switchMathExpressionWithResult()
                        }
                        else -> viewModel.updateMathExpression(currentView.text.toString())
                    }
                }
            }
        }
    }
}