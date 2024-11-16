package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.R
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

        // Configuración del adaptador y RecyclerView
        adapter = ArtistAdapter(listOf()) { artist ->
            val bundle = Bundle().apply { putInt("artistId", artist.artistId) }
            findNavController().navigate(R.id.action_artistFragment_to_artistDetailFragment, bundle)
        }
        binding.artistsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.artistsRecyclerView.adapter = adapter

        // Observador de la lista de artistas
        artistViewModel.artists.observe(viewLifecycleOwner) { artists ->
            adapter.updateArtists(artists)
        }

        // Observador del estado de carga
        artistViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Manejo de errores de red
        artistViewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError && !artistViewModel.isNetworkErrorShown.value!!) {
                Toast.makeText(context, artistViewModel.networkErrorMessage.value, Toast.LENGTH_LONG).show()
                artistViewModel.onNetworkErrorShown()
            }
        }
    }
}
