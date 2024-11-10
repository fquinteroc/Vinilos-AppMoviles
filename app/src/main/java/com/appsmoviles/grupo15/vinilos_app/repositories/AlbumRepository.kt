package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter
import com.appsmoviles.grupo15.vinilos_app.network.CacheManager

class AlbumRepository(val application: Application) {

    suspend fun refreshData(): List<Album> {
        // Verificar si el listado de álbumes está en caché
        val cachedAlbums = CacheManager.getInstance(application.applicationContext).getAlbumList()
        return if (cachedAlbums != null) {
            cachedAlbums
        } else {
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbumList(albums)
            albums
        }
    }
}
