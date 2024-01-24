package com.example.calculatorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.calculatorapp.R
import com.example.calculatorapp.databinding.ButtonPanelBinding
import com.example.calculatorapp.view_models.CalculatorViewModel

class ButtonFragment : Fragment(R.layout.button_panel){
    private var binding: ButtonPanelBinding? = null

    private val viewModel: CalculatorViewModel by activityViewModels()

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
                    viewModel.updateMathExpression((currentView as Button).text.toString())
                }
            }
        }
    }
}