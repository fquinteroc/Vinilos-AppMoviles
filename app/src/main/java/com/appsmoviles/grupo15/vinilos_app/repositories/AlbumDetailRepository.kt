package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter
import com.appsmoviles.grupo15.vinilos_app.network.CacheManager

class AlbumDetailRepository(val application: Application) {

    suspend fun getAlbumDetail(albumId: Int): Album {
        // Verificar si el detalle del álbum está en caché
        val cachedAlbum = CacheManager.getInstance(application.applicationContext).getAlbumDetail(albumId)
        return if (cachedAlbum != null) {
            cachedAlbum
        } else {
            // Hacer solicitud de red y almacenar en caché
            val album = NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbumDetail(albumId, album)
            album
        }
    }
}

