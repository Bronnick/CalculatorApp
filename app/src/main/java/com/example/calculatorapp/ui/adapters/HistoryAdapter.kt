package com.example.calculatorapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.database.entities.HistoryItem
import com.example.calculatorapp.databinding.HistoryItemBinding

class HistoryAdapter(
    private val history: List<HistoryItem>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = history[position]

        holder.mathExpression.text = historyItem.mathExpression
        holder.result.text = "=${historyItem.result}"
        holder.itemView.setOnClickListener {
            onItemClick(holder.result.text.toString().drop(1))
        }
    }
}

class HistoryViewHolder(
    binding: HistoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    val mathExpression = binding.textViewHistoryItemMathExpression
    val result = binding.textViewHistoryItemMathResult
}