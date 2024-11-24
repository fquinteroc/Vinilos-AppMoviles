package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.databinding.CollectorDetailFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.AlbumsAdapter
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.ArtistAdapter
import com.appsmoviles.grupo15.vinilos_app.viewmodels.CollectorDetailViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CollectorDetailFragment : Fragment() {

    private val collectorDetailViewModel: CollectorDetailViewModel by viewModels()
    private lateinit var binding: CollectorDetailFragmentBinding
    private lateinit var favoriteArtistsAdapter: ArtistAdapter
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val collectorId = arguments?.getInt("collectorId") ?: 0
        if (collectorId > 0) {
            collectorDetailViewModel.getCollectorDetail(collectorId)
        } else {
            showError("ID del coleccionista inválido.")
        }
        setupSwipeToRefresh(collectorId)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val snapHelperArtists = PagerSnapHelper()
        val snapHelperAlbums = PagerSnapHelper()

        favoriteArtistsAdapter = ArtistAdapter(listOf()) { artist ->
            Toast.makeText(requireContext(), "Click en ${artist.name}", Toast.LENGTH_SHORT).show()
        }

        binding.favoriteArtistsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteArtistsAdapter
            snapHelperArtists.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visiblePosition = layoutManager.findFirstVisibleItemPosition() + 1
                    binding.artistPositionIndicator.text =
                        "$visiblePosition / ${favoriteArtistsAdapter.itemCount}"
                    binding.artistProgressBar.progress =
                        (visiblePosition * 100) / favoriteArtistsAdapter.itemCount
                }
            })
        }

        albumsAdapter = AlbumsAdapter(listOf()) { album ->
            Toast.makeText(requireContext(), "Click en ${album.name}", Toast.LENGTH_SHORT).show()
        }

        binding.collectorAlbumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumsAdapter
            snapHelperAlbums.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visiblePosition = layoutManager.findFirstVisibleItemPosition() + 1
                    binding.albumPositionIndicator.text =
                        "$visiblePosition / ${albumsAdapter.itemCount}"
                    binding.albumProgressBar.progress =
                        (visiblePosition * 100) / albumsAdapter.itemCount
                }
            })
        }
    }

    private fun setupSwipeToRefresh(collectorId: Int) {
        val swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            collectorDetailViewModel.getCollectorDetail(collectorId)

            // Detén el indicador de actualización cuando los datos se actualicen
            collectorDetailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (!isLoading) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun setupObservers() {
        collectorDetailViewModel.collector.observe(viewLifecycleOwner) { collector ->
            if (collector != null) {
                binding.collector = collector
            } else {
                showError("No se pudo cargar la información del coleccionista.")
            }
        }

        collectorDetailViewModel.favoriteArtists.observe(viewLifecycleOwner) { artists ->
            favoriteArtistsAdapter.updateArtists(artists)
            binding.artistPositionIndicator.text = "1 / ${artists.size}"
        }

        collectorDetailViewModel.collectorAlbums.observe(viewLifecycleOwner) { albums ->
            albumsAdapter.updateAlbums(albums)
            binding.albumPositionIndicator.text = "1 / ${albums.size}"
        }

        collectorDetailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("CollectorDetailFragment", "isLoading: $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        collectorDetailViewModel.networkErrorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showError(it)
                collectorDetailViewModel.resetNetworkErrorMessage()
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}