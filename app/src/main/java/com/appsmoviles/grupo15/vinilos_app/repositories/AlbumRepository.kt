package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {

    suspend fun refreshData(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}
