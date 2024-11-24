package com.appsmoviles.grupo15.vinilos_app.ui

import android.content.Context
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
import com.appsmoviles.grupo15.vinilos_app.databinding.AlbumDetailFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.TracksAdapter
import com.appsmoviles.grupo15.vinilos_app.viewmodels.AlbumDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailFragment : Fragment() {

    private val albumDetailViewModel: AlbumDetailViewModel by viewModels()
    private lateinit var binding: AlbumDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumId = arguments?.getInt("albumId") ?: 0
        setupRoleVisibility()
        setupObservers(albumId)
        setupRecyclerView()
        setupPullToRefresh(albumId)

        binding.addTrackButton.setOnClickListener {
            val bundle = Bundle().apply { putInt("albumId", albumId) }
            findNavController().navigate(R.id.action_albumDetailFragment_to_addTrackFragment, bundle)
        }
    }

    private fun setupRoleVisibility() {
        val sharedPref = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val selectedRole = sharedPref.getString("selected_role", "Usuario")
        binding.addTrackButton.visibility = if (selectedRole == "Coleccionista") View.VISIBLE else View.GONE
    }

    private fun setupObservers(albumId: Int) {
        albumDetailViewModel.getAlbumDetail(albumId)
        albumDetailViewModel.getAlbumTracks(albumId)

        albumDetailViewModel.album.observe(viewLifecycleOwner, Observer { album ->
            album?.let {
                binding.albumName.text = it.name
                binding.albumReleaseDate.text = formatReleaseDate(it.releaseDate)
                binding.albumGenre.text = it.genre
                binding.albumRecordLabel.text = it.recordLabel
                binding.albumDescription.text = it.description

                Glide.with(binding.albumCover.context)
                    .load(it.cover)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.error)
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(false)
                    )
                    .into(binding.albumCover)
            }
        })

        albumDetailViewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            val tracksAdapter = binding.tracksRecyclerView.adapter as TracksAdapter
            if (tracks.isNullOrEmpty()) {
                binding.tracksRecyclerView.visibility = View.GONE
                binding.noTracksMessage.visibility = View.VISIBLE
            } else {
                binding.tracksRecyclerView.visibility = View.VISIBLE
                binding.noTracksMessage.visibility = View.GONE
                tracksAdapter.updateTracks(tracks)
            }
        }

        albumDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        albumDetailViewModel.networkErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                albumDetailViewModel.resetNetworkErrorMessage()
            }
        })
    }

    private fun setupRecyclerView() {
        val tracksAdapter = TracksAdapter(listOf())
        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.tracksRecyclerView.adapter = tracksAdapter
    }

    private fun setupPullToRefresh(albumId: Int) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            albumDetailViewModel.getAlbumDetail(albumId)
            albumDetailViewModel.getAlbumTracks(albumId)

            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun formatReleaseDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate ?: "")
        } catch (e: Exception) {
            date
        }
    }
}
