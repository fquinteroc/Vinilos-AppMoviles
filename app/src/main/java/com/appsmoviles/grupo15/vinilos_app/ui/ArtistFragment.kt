package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.databinding.ArtistFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.ArtistAdapter
import com.appsmoviles.grupo15.vinilos_app.viewmodels.ArtistViewModel

class ArtistFragment : Fragment() {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var binding: ArtistFragmentBinding
    private lateinit var adapter: ArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArtistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArtistAdapter(listOf()) { artist ->
            // Acción al hacer clic en un artista
        }
        binding.artistsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.artistsRecyclerView.adapter = adapter

        artistViewModel.artists.observe(viewLifecycleOwner) { artists ->
            adapter.updateArtists(artists)
            binding.progressBar.visibility = View.GONE
        }

        artistViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        artistViewModel.fetchArtists()
    }
}