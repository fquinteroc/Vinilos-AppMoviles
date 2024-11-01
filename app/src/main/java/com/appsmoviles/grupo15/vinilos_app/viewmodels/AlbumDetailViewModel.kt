package com.appsmoviles.grupo15.vinilos_app.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumDetailViewModel (application: Application) : AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    fun getAlbumDetail(albumId: Int) {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbumDetail(albumId, { album ->
            _album.postValue(album)
        }, {
            // Manejar errores
        })
    }
}