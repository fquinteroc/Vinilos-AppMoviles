package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.repositories.AlbumDetailRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers


class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val albumDetailRepository = AlbumDetailRepository(application)

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage


    fun getAlbumDetail(albumId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val album = withContext(Dispatchers.IO) {
                    albumDetailRepository.getAlbumDetail(albumId)
                }
                _album.postValue(album)
                _eventNetworkError.postValue(false)
            } catch (e: Exception) {
                _eventNetworkError.postValue(true)
                _networkErrorMessage.postValue("Error al cargar el detalle del álbum, por favor intenta de nuevo.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun onNetworkErrorShown() {
        _eventNetworkError.value = false
    }

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }

}
