package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.appsmoviles.grupo15.vinilos_app.databinding.AlbumDetailFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.viewmodels.AlbumDetailViewModel
import com.bumptech.glide.Glide
import com.appsmoviles.grupo15.vinilos_app.R

class AlbumDetailFragment : Fragment() {

    private val albumDetailViewModel: AlbumDetailViewModel by viewModels()
    private lateinit var binding: AlbumDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumId = arguments?.getInt("albumId") ?: 0
        albumDetailViewModel.getAlbumDetail(albumId)

        albumDetailViewModel.album.observe(viewLifecycleOwner, Observer { album ->
            album?.let {
                binding.album = it
                Glide.with(binding.albumCover.context)
                    .load(it.cover)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.error)
                    .into(binding.albumCover)
            }
        })

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
}
