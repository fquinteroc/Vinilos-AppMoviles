package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.databinding.ArtistDetailFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.viewmodels.ArtistDetailViewModel
import com.bumptech.glide.Glide
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.AlbumsAdapter

class ArtistDetailFragment : Fragment() {

    private val artistDetailViewModel: ArtistDetailViewModel by viewModels()
    private lateinit var binding: ArtistDetailFragmentBinding
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistId = arguments?.getInt("artistId") ?: 0
        setupRecyclerView()
        setupSwipeToRefresh(artistId)
        setupObservers()
        artistDetailViewModel.getArtistDetail(artistId)
    }

    private fun setupRecyclerView() {
        albumsAdapter = AlbumsAdapter(listOf()) {}
        binding.artistAlbumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumsAdapter
        }
    }

    private fun setupSwipeToRefresh(artistId: Int) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            artistDetailViewModel.getArtistDetail(artistId)
        }
    }

    private fun setupObservers() {
        artistDetailViewModel.artist.observe(viewLifecycleOwner) { artist ->
            artist?.let {
                binding.artist = it
                Glide.with(binding.artistImage.context)
                    .load(it.image)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.error)
                    .into(binding.artistImage)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        artistDetailViewModel.artistAlbums.observe(viewLifecycleOwner) { albums ->
            albumsAdapter.updateAlbums(albums)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        artistDetailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        artistDetailViewModel.networkErrorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                artistDetailViewModel.resetNetworkErrorMessage()
            }
        }
    }
}