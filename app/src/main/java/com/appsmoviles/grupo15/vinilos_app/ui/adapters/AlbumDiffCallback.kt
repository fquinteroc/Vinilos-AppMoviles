package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.appsmoviles.grupo15.vinilos_app.models.Album

class AlbumsDiffCallback(
    private val oldList: List<Album>,
    private val newList: List<Album>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].albumId == newList[newItemPosition].albumId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
