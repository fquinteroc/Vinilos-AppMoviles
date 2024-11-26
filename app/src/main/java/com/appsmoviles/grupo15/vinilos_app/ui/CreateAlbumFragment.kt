package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.util.Patterns
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
            if (validateInputs()) {
                val album = Album(
                    albumId = 0,
                    name = binding.etName.text.toString(),
                    releaseDate = binding.etReleaseDate.text.toString(),
                    description = binding.etDescription.text.toString(),
                    genre = binding.actvGenre.text.toString(),
                    recordLabel = binding.actvRecordLabel.text.toString(),
                    cover = binding.etCoverUrl.text.toString()
                )

                createAlbumViewModel.createAlbum(album)
            }
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

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validar nombre del álbum
        if (binding.etName.text.isNullOrBlank()) {
            binding.tilName.error = "El nombre del álbum es obligatorio"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        // Validar fecha de lanzamiento
        val releaseDate = binding.etReleaseDate.text.toString()
        if (!releaseDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            binding.tilReleaseDate.error = "La fecha debe tener el formato YYYY-MM-DD"
            isValid = false
        } else {
            binding.tilReleaseDate.error = null
        }

        // Validar descripción
        if (binding.etDescription.text.isNullOrBlank()) {
            binding.tilDescription.error = "La descripción es obligatoria"
            isValid = false
        } else {
            binding.tilDescription.error = null
        }

        // Validar género
        val selectedGenre = binding.actvGenre.text.toString()
        if (selectedGenre.isEmpty() || !listOf("Classical", "Salsa", "Rock", "Folk").contains(selectedGenre)) {
            binding.tilGenre.error = "Selecciona un género válido"
            isValid = false
        } else {
            binding.tilGenre.error = null
        }

        // Validar sello discográfico
        val selectedRecordLabel = binding.actvRecordLabel.text.toString()
        if (selectedRecordLabel.isEmpty() || !listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records").contains(selectedRecordLabel)) {
            binding.tilRecordLabel.error = "Selecciona un sello discográfico válido"
            isValid = false
        } else {
            binding.tilRecordLabel.error = null
        }

        // Validar URL de la portada
        val coverUrl = binding.etCoverUrl.text.toString()
        if (!Patterns.WEB_URL.matcher(coverUrl).matches()) {
            binding.tilCoverUrl.error = "Introduce una URL válida"
            isValid = false
        } else {
            binding.tilCoverUrl.error = null
        }

        return isValid
    }
}