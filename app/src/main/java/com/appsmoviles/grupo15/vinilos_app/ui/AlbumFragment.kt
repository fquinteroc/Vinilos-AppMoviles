package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.databinding.AlbumFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.AlbumsAdapter
import com.appsmoviles.grupo15.vinilos_app.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {

    private val albumViewModel: AlbumViewModel by viewModels()
    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var binding: AlbumFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeToRefresh()
        setupObservers()

        albumViewModel.fetchAlbums()
    }

    private fun setupRecyclerView() {
        albumsAdapter = AlbumsAdapter(listOf()) { album ->
            val bundle = Bundle().apply { putInt("albumId", album.albumId) }
            findNavController().navigate(R.id.action_albumFragment_to_albumDetailFragment, bundle)
        }
        binding.albumsRv.layoutManager = LinearLayoutManager(context)
        binding.albumsRv.adapter = albumsAdapter
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            albumViewModel.fetchAlbums()
        }
    }

    private fun setupObservers() {
        albumViewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            albums?.let {
                albumsAdapter.updateAlbums(it)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })

        albumViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        albumViewModel.networkErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                albumViewModel.resetNetworkErrorMessage()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}
