package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.albumsRv)
        progressBar = view.findViewById(R.id.progressBar)

        albumsAdapter = AlbumsAdapter(listOf()) { album ->
            val bundle = Bundle().apply { putInt("albumId", album.albumId) }
            findNavController().navigate(R.id.action_albumFragment_to_albumDetailFragment, bundle)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = albumsAdapter

        albumViewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            albums?.let {
                albumsAdapter.updateAlbums(it)
            }
        })

        albumViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        albumViewModel.fetchAlbums()
    }
}
