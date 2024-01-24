package com.example.calculatorapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calculatorapp.R
import com.example.calculatorapp.databinding.TextPanelBinding

class MathExpressionFragment : Fragment(R.layout.text_panel) {
    private var binding: TextPanelBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TextPanelBinding.inflate(inflater, container, false)
        return binding?.root
    }


}