package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.repositories.ArtistDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> get() = _artist

    private val _artistAlbums = MutableLiveData<List<Album>>()
    val artistAlbums: LiveData<List<Album>> get() = _artistAlbums

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage

    private val artistDetailRepository = ArtistDetailRepository(application)

    fun getArtistDetail(artistId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val artist = withContext(Dispatchers.IO) {
                    artistDetailRepository.getArtistDetail(artistId)
                }
                val formattedDate = formatDate(artist.birthDate)
                val updatedArtist = artist.copy(birthDate = formattedDate)
                _artist.postValue(updatedArtist)

                // Obtener los álbumes del artista
                val albums = withContext(Dispatchers.IO) {
                    artistDetailRepository.getArtistAlbums(artistId)
                }
                _artistAlbums.postValue(albums)

                _eventNetworkError.postValue(false)
            } catch (e: Exception) {
                _eventNetworkError.postValue(true)
                _networkErrorMessage.postValue("Error al cargar el detalle del artista, por favor intenta de nuevo.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            dateString
        }
    }

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }
}
