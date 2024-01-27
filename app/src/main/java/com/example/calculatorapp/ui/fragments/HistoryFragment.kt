package com.example.calculatorapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.calculatorapp.R
import com.example.calculatorapp.database.entities.HistoryItem
import com.example.calculatorapp.databinding.HistoryFragmentBinding
import com.example.calculatorapp.ui.adapters.HistoryAdapter
import com.example.calculatorapp.view_models.CalculatorViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(R.layout.history_fragment) {

    private var binding: HistoryFragmentBinding? = null
    private var adapter: HistoryAdapter? = null

    private val viewModel: CalculatorViewModel by activityViewModels()

    private var history: List<HistoryItem> = ArrayList()

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
        adapter = HistoryAdapter(history)
        binding?.rvHistory?.adapter = adapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.history.collectLatest { list ->
                history = list
                history.forEach {
                    Log.d("myLogs", it.toString())
                }
                adapter = HistoryAdapter(history)
                binding?.rvHistory?.adapter = adapter
                Log.d("myLogs", "end of collectLatest")
                //binding?.test?.text = "TEXT CHNAGED"
            }
            Log.d("myLogs", "finished collecting")
        }
    }
}