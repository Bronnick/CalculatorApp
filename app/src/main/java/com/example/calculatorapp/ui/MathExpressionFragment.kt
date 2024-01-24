package com.example.calculatorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
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
            binding?.editTextMathExpression?.setText(it)
        }
    }
}