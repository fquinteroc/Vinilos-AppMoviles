package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Track
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumDetailRepository(val application: Application) {

    private val albumsDao = VinilosRoomDatabase.getDatabase(application).albumsDao()

    suspend fun getAlbumDetail(albumId: Int): Album {
        // Consultar primero en la base de datos local
        val cachedAlbum = albumsDao.getAlbumDetail(albumId)
        return if (cachedAlbum != null) {
            cachedAlbum
        } else {
            // Si no está en caché, hacer solicitud de red y almacenar en Room
            val album = NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId)
            albumsDao.insertAll(listOf(album))
            album
        }
    }

    suspend fun getAlbumTracks(albumId: Int): List<Track> {
        return NetworkServiceAdapter.getInstance(application).getAlbumTracks(albumId)
    }
}

