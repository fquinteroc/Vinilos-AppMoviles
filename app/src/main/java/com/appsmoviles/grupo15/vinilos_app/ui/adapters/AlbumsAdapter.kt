package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.databinding.AlbumItemBinding
import com.appsmoviles.grupo15.vinilos_app.models.Album

class AlbumsAdapter(private var albums: List<Album>) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }

    inner class AlbumViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()
        }
    }
}
