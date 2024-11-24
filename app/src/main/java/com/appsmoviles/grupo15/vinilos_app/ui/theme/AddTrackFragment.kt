package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appsmoviles.grupo15.vinilos_app.databinding.AddTrackFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.viewmodels.TrackAssociationViewModel

@Suppress("DEPRECATION")
class AddTrackFragment : Fragment() {

    private lateinit var binding: AddTrackFragmentBinding
    private val viewModel: TrackAssociationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTrackFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumId = arguments?.getInt("albumId") ?: return

        binding.addTrackButton.setOnClickListener {
            val trackName = binding.trackNameEditText.text.toString()
            val trackDuration = binding.trackDurationEditText.text.toString()

            if (trackName.isEmpty() || trackDuration.isEmpty()) {
                Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addTrackToAlbum(albumId, trackName, trackDuration)
        }

        viewModel.isTrackAdded.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Canción agregada con éxito", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, "Error al agregar la canción", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
