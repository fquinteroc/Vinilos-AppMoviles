package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase

class ArtistRepository(private val application: Application) {

    private val artistsDao = VinilosRoomDatabase.getDatabase(application).artistsDao()

    suspend fun refreshData(): List<Artist> {
        val cachedArtists = artistsDao.getArtists()
        return cachedArtists.ifEmpty {
            val artists = NetworkServiceAdapter.getInstance(application).getArtists()
            artistsDao.insertAll(artists)
            artists
        }
    }
}