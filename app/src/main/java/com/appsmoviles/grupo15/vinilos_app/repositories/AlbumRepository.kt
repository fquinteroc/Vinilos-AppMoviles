package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase

class AlbumRepository(private val application: Application) {

    private val albumsDao = VinilosRoomDatabase.getDatabase(application).albumsDao()

    suspend fun refreshData(): List<Album> {
        val cachedAlbums = albumsDao.getAlbums()
        return cachedAlbums.ifEmpty {
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            val sortedAlbums = albums.sortedBy { it.name }
            albumsDao.insertAll(sortedAlbums)
            sortedAlbums
        }
    }
}
