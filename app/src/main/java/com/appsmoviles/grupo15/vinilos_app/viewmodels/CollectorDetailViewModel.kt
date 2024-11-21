package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.models.Collector
import com.appsmoviles.grupo15.vinilos_app.repositories.CollectorDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _collector = MutableLiveData<Collector>()
    val collector: LiveData<Collector> get() = _collector

    private val _favoriteArtists = MutableLiveData<List<Artist>>()
    val favoriteArtists: LiveData<List<Artist>> get() = _favoriteArtists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage

    private val collectorDetailRepository = CollectorDetailRepository(application)

    private val _collectorAlbums = MutableLiveData<List<Album>>()
    val collectorAlbums: LiveData<List<Album>> get() = _collectorAlbums

    fun getCollectorDetail(collectorId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val collector = withContext(Dispatchers.IO) {
                    collectorDetailRepository.getCollectorDetail(collectorId)
                }
                _collector.postValue(collector)

                val albums = withContext(Dispatchers.IO) {
                    collectorDetailRepository.getCollectorAlbums(collectorId)
                }
                _collectorAlbums.postValue(albums)

                val artists = withContext(Dispatchers.IO) {
                    collectorDetailRepository.getCollectorArtists(collectorId)
                }
                _favoriteArtists.postValue(artists)

                _eventNetworkError.postValue(false)
            } catch (e: Exception) {
                _eventNetworkError.postValue(true)
                _networkErrorMessage.postValue("Error al cargar el detalle del coleccionista, sus álbumes y artistas, por favor intenta de nuevo.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }
}

