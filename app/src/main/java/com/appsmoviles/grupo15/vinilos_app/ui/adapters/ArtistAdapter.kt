package com.appsmoviles.grupo15.vinilos_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.databinding.ArtistItemBinding
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.bumptech.glide.Glide
import com.appsmoviles.grupo15.vinilos_app.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ArtistAdapter(
    private var artists: List<Artist>,
    private val onArtistClick: (Artist) -> Unit
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount(): Int = artists.size

    fun updateArtists(newArtists: List<Artist>) {
        val diffCallback = ArtistDiffCallback(artists, newArtists)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        artists = newArtists
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ArtistViewHolder(private val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.artist = artist
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onArtistClick(artist)
            }

            Glide.with(binding.artistImage.context)
                .load(artist.image)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(binding.artistImage)

            val maxLength = 100
            val descriptionText = artist.description
            val description = if (descriptionText.length > maxLength) {
                "${descriptionText.substring(0, maxLength)}... (Ver más)"
            } else {
                descriptionText
            }
            binding.artistDescription.text = description
        }
    }
}