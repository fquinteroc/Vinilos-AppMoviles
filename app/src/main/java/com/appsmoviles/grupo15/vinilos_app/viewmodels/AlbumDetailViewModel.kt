package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Track
import com.appsmoviles.grupo15.vinilos_app.repositories.AlbumDetailRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers


class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _eventNetworkError = MutableLiveData<Boolean>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val albumDetailRepository = AlbumDetailRepository(application)

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> get() = _tracks


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

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }

    fun getAlbumTracks(albumId: Int) {
        viewModelScope.launch {
            try {
                val tracks = withContext(Dispatchers.IO) {
                    albumDetailRepository.getAlbumTracks(albumId)
                }
                _tracks.postValue(tracks)
            } catch (e: Exception) {
                _networkErrorMessage.postValue("Error al cargar las canciones, por favor intenta de nuevo.")
            }
        }
    }

}
