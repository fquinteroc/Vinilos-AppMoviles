package com.appsmoviles.grupo15.vinilos_app.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.viewmodels.AlbumDetailViewModel
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {

    // Recibe el argumento albumId pasado por navegación
    private val args: AlbumDetailFragmentArgs by navArgs()

    // Usa el ViewModel con el albumId
    private val viewModel: AlbumDetailViewModel by viewModels {
        AlbumDetailViewModel.Factory(requireActivity().application, args.albumId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumCover: ImageView = view.findViewById(R.id.album_cover)
        val albumName: TextView = view.findViewById(R.id.album_name)
        val albumArtist: TextView = view.findViewById(R.id.album_artist)
        val albumGenre: TextView = view.findViewById(R.id.album_genre)
        val albumReleaseDate: TextView = view.findViewById(R.id.album_release_date)
        val albumDescription: TextView = view.findViewById(R.id.album_description)

        // Observa los datos del álbum y actualiza la UI cuando el detalle esté disponible
        viewModel.albumDetail.observe(viewLifecycleOwner, Observer { album ->
            album?.let { showAlbumDetails(it, albumCover, albumName, albumArtist, albumGenre, albumReleaseDate, albumDescription) }
        })

        // Observa el estado de error de red y maneja el error
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) showNetworkError()
        })
    }

    private fun showAlbumDetails(album: Album, albumCover: ImageView, albumName: TextView, albumArtist: TextView, albumGenre: TextView, albumReleaseDate: TextView, albumDescription: TextView) {
        albumName.text = album.name
        albumGenre.text = album.genre
        albumReleaseDate.text = "Lanzado: ${album.releaseDate}"
        albumDescription.text = album.description

        // Cargar la imagen de portada con Glide
        Glide.with(this)
            .load(album.cover)
            .into(albumCover)
        albumCover.contentDescription = "Portada del álbum ${album.name}"
    }

    private fun showNetworkError() {
        // Muestra un mensaje de error, como un Toast o un mensaje en el layout
        // Ejemplo de Toast
        Toast.makeText(requireContext(), "Error al cargar los detalles del álbum", Toast.LENGTH_SHORT).show()
        viewModel.onNetworkErrorShown()
    }
}