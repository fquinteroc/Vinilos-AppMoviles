package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.databinding.CollectorItemBinding
import com.appsmoviles.grupo15.vinilos_app.models.Collector

class CollectorAdapter(
    private var collectors: List<Collector>,
    private val onCollectorClick: (Collector) -> Unit
) : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val binding = CollectorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.bind(collectors[position])
    }

    override fun getItemCount(): Int = collectors.size

    fun updateCollectors(newCollectors: List<Collector>) {
        val diffCallback = CollectorsDiffCallback(collectors, newCollectors)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        collectors = newCollectors
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CollectorViewHolder(private val binding: CollectorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collector: Collector) {
            binding.collector = collector
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onCollectorClick(collector)
            }
        }
    }
}