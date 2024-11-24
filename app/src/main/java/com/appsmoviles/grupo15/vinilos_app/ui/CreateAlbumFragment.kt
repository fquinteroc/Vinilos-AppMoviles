package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appsmoviles.grupo15.vinilos_app.databinding.CreateAlbumFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.viewmodels.CreateAlbumViewModel

class CreateAlbumFragment : Fragment() {

    private lateinit var binding: CreateAlbumFragmentBinding
    private val createAlbumViewModel: CreateAlbumViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar géneros disponibles
        val genres = listOf("Classical", "Salsa", "Rock", "Folk")
        val genreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genres)
        binding.actvGenre.setAdapter(genreAdapter)

        // Configurar sellos discográficos disponibles
        val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        val recordLabelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, recordLabels)
        binding.actvRecordLabel.setAdapter(recordLabelAdapter)

        binding.btnSaveAlbum.setOnClickListener {
            val selectedGenre = binding.actvGenre.text.toString()
            val selectedRecordLabel = binding.actvRecordLabel.text.toString()

            if (selectedGenre.isEmpty() || !genres.contains(selectedGenre)) {
                Toast.makeText(context, "Por favor selecciona un género válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedRecordLabel.isEmpty() || !recordLabels.contains(selectedRecordLabel)) {
                Toast.makeText(context, "Por favor selecciona un sello discográfico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val album = Album(
                albumId = 0,
                name = binding.etName.text.toString(),
                releaseDate = binding.etReleaseDate.text.toString(),
                description = binding.etDescription.text.toString(),
                genre = selectedGenre,
                recordLabel = selectedRecordLabel,
                cover = binding.etCoverUrl.text.toString()
            )

            createAlbumViewModel.createAlbum(album)
        }

        setupObservers()
    }

    private fun setupObservers() {
        createAlbumViewModel.isSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Álbum creado exitosamente", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        createAlbumViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}