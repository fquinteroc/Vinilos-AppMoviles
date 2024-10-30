package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumDetailViewModel(application: Application, private val albumId: Int) : AndroidViewModel(application) {

    private val _albumDetail = MutableLiveData<Album>()
    val albumDetail: LiveData<Album> get() = _albumDetail

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    init {
        loadAlbumDetail()
    }

    private fun loadAlbumDetail() {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbum(albumId,
            onComplete = { album ->
                _albumDetail.value = album
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },
            onError = { error ->
                _eventNetworkError.value = true
                handleNetworkError(error)
            }
        )
    }

    private fun handleNetworkError(error: VolleyError) {
        _isNetworkErrorShown.value = false
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}