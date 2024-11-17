package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.appsmoviles.grupo15.vinilos_app.models.Collector

class CollectorsDiffCallback(
    private val oldList: List<Collector>,
    private val newList: List<Collector>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}