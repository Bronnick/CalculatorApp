package com.example.calculatorapp.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calculatorapp.R
import com.example.calculatorapp.databinding.TextPanelBinding
import com.example.calculatorapp.utils.getTextResizeAnimation
import com.example.calculatorapp.utils.vibrate
import com.example.calculatorapp.view_models.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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

        viewModel.equalsPressedFlag.observe(activity as LifecycleOwner) {
            val editTextMathExpression = binding?.editTextMathExpression
            val editTextResult = binding?.editTextResult

            val translationAnimation = ObjectAnimator.ofFloat(editTextMathExpression, "translationY", 300f, 0f)

            val px = editTextMathExpression?.textSize
            val sp = (px ?: 1.0f) / resources.displayMetrics.scaledDensity
            val initialValue = (editTextResult?.textSize ?: 1.0f) / resources.displayMetrics.scaledDensity
            val textResizeAnimation = getTextResizeAnimation(editTextMathExpression,
                try { editTextMathExpression?.text?.toString()?.length!! } catch(e:Exception) {0}, sp, initialValue)

            val animSet = AnimatorSet()
            animSet.play(translationAnimation).with(textResizeAnimation)
            animSet.duration = 200L
            animSet.interpolator = AccelerateInterpolator()
            animSet.start()
        }

        viewModel.mathExpression.observe(activity as LifecycleOwner) {
            val editTextMathExpression = binding?.editTextMathExpression

            val length = it.length

            editTextMathExpression?.setText(it)

            viewModel.evaluateExpression(it)

            val px = editTextMathExpression?.textSize
            val sp = (px ?: 1.0f) / resources.displayMetrics.scaledDensity

            val initialValue = (editTextMathExpression?.textSize ?: 1.0f) / resources.displayMetrics.scaledDensity

            val animation = getTextResizeAnimation(editTextMathExpression, length, sp, initialValue)

            animation.duration = 100L
            animation.interpolator = AccelerateInterpolator()
            animation.start()
            viewModel.updateMathExpressionPreviousLength()
        }

        viewModel.result.observe(activity as LifecycleOwner) {
            binding?.editTextResult?.setText(it)
        }

        binding?.backspaceButton?.setOnClickListener {
            viewModel.removeLastSymbol()
            vibrate(requireActivity())
        }

        binding?.historyButton?.setOnClickListener {
            if(findNavController().currentDestination?.id == R.id.buttonFragment) {
                findNavController().navigate(R.id.action_buttonFragment_to_historyFragment)
            } else {
                findNavController().popBackStack()
            }
            vibrate(requireActivity())
        }

        binding?.editTextMathExpression?.showSoftInputOnFocus = false

        collectDatabaseState()
    }

    private fun collectDatabaseState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.history.collectLatest { list ->
                binding?.historyButton?.isEnabled = list.isNotEmpty()
                binding?.historyButton?.alpha = if(list.isNotEmpty()) 1.0f else 0.2f
            }
        }
    }
}