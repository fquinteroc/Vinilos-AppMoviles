package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TrackAssociationViewModel(application: Application) : AndroidViewModel(application) {

    private val _isTrackAdded = MutableLiveData<Boolean>()
    val isTrackAdded: LiveData<Boolean> get() = _isTrackAdded

    fun addTrackToAlbum(albumId: Int, trackName: String, trackDuration: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val body = JSONObject().apply {
                        put("name", trackName)
                        put("duration", trackDuration)
                    }
                    NetworkServiceAdapter.getInstance(getApplication()).postTrack(
                        body,
                        albumId,
                        { _isTrackAdded.postValue(true) },
                        { _isTrackAdded.postValue(false) }
                    )
                }
            } catch (e: Exception) {
                _isTrackAdded.postValue(false)
            }
        }
    }
}
