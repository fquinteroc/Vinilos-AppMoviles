package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.repositories.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> get() = _artists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val artistRepository = ArtistRepository(application)

    fun fetchArtists() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val artists = withContext(Dispatchers.IO) {
                    artistRepository.refreshData()
                }
                _artists.postValue(artists)
            } catch (e: Exception) {
                _artists.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}