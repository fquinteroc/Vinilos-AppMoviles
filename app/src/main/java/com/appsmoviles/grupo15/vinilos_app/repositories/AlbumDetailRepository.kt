package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumDetailRepository(val application: Application) {

    suspend fun getAlbumDetail(albumId: Int): Album {
        return NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId)
    }
}

