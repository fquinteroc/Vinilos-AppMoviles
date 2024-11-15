package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.appsmoviles.grupo15.vinilos_app.models.Artist

class ArtistDiffCallback(
    private val oldList: List<Artist>,
    private val newList: List<Artist>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparar por ID ya que es único para cada artista
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparar el contenido completo del artista
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}