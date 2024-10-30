package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.viewmodels.AlbumViewModel
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.AlbumsAdapter

class AlbumFragment : Fragment() {

    private val albumViewModel: AlbumViewModel by viewModels()
    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.albumsRv)
        albumsAdapter = AlbumsAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = albumsAdapter

        // Observa la lista de álbumes en el ViewModel
        albumViewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            albums?.let {
                albumsAdapter.updateAlbums(it)
            }
        })

        // Llama a la función para obtener los álbumes de la API
        albumViewModel.fetchAlbums()
    }
}