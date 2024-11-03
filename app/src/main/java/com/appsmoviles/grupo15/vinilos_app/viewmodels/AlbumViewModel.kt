package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.repositories.AlbumRepository

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage

    // Instancia de AlbumRepository
    private val albumRepository = AlbumRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        _isLoading.value = true
        albumRepository.refreshData({ albums ->
            _albums.postValue(albums)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
            _isLoading.value = false
        }, {
            _eventNetworkError.value = true
            _isLoading.value = false
            _networkErrorMessage.value = "Error al cargar el listado de álbumes, por favor intenta de nuevo."
        })
    }

    fun fetchAlbums() {
        refreshDataFromNetwork()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }
}
