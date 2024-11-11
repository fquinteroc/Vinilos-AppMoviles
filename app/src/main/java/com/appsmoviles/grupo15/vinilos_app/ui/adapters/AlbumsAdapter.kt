package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.databinding.AlbumItemBinding
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.bumptech.glide.Glide
import com.appsmoviles.grupo15.vinilos_app.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class AlbumsAdapter(
    private var albums: List<Album>,
    private val onAlbumClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

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

            itemView.setOnClickListener {
                onAlbumClick(album)
            }

            Glide.with(binding.albumCover.context)
                .load(album.cover)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.error)
                .into(binding.albumCover)

            Glide.with(binding.albumCover.context)
                .load(album.cover)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                )
                .into(binding.albumCover)

            val maxLength = 80
            val descriptionText = album.description
            val description = if (descriptionText.length > maxLength) {
                descriptionText.substring(0, maxLength) + "... (Ver más)"
            } else {
                "$descriptionText... (Ver más)"
            }

            binding.albumDescription.text = description

        }
    }
}
