package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.repositories.AlbumRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val albumRepository = AlbumRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val albums = withContext(Dispatchers.IO) {
                    albumRepository.refreshData()
                }
                _albums.postValue(albums)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            } catch (e: Exception) {
                _eventNetworkError.postValue(true)
                _networkErrorMessage.postValue("Error al cargar el listado de álbumes, por favor intenta de nuevo.")
            } finally {
                _isLoading.postValue(false)
            }
        }
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
