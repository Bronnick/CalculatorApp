package com.example.calculatorapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calculatorapp.R
import com.example.calculatorapp.databinding.HistoryFragmentBinding
import com.example.calculatorapp.ui.adapters.HistoryAdapter
import com.example.calculatorapp.view_models.CalculatorViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(R.layout.history_fragment) {

    private var binding: HistoryFragmentBinding? = null
    private var adapter: HistoryAdapter? = null

    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectUiState()
    }

    private fun initView() {
        binding?.rvHistory?.adapter = adapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.history.collectLatest { list ->
                adapter = HistoryAdapter(list) { exp ->
                    viewModel.setMathExpression(exp)
                    findNavController().popBackStack()
                }
                binding?.rvHistory?.adapter = adapter
            }
        }
    }
}