package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class ArtistDetailRepository(private val application: Application) {

    private val artistsDao = VinilosRoomDatabase.getDatabase(application).artistsDao()
    private val albumsDao = VinilosRoomDatabase.getDatabase(application).albumsDao()

    suspend fun getArtistDetail(artistId: Int): Artist {
        // Consultar primero en la base de datos local
        val cachedArtist = artistsDao.getArtistDetail(artistId)
        return if (cachedArtist != null) {
            cachedArtist
        } else {
            // Si no está en caché, hacer solicitud de red y almacenar en Room
            val artist = NetworkServiceAdapter.getInstance(application).getArtistDetail(artistId)
            artistsDao.insert(artist)
            artist
        }
    }

    suspend fun getArtistAlbums(artistId: Int): List<Album> {
        // Obtener los IDs de los álbumes del artista desde la API
        val albumIds = NetworkServiceAdapter.getInstance(application).getArtistAlbums(artistId)

        // Consultar en la base de datos local primero
        val cachedAlbums = albumsDao.getAlbumsByArtist(albumIds)
        return cachedAlbums.ifEmpty {
            // Obtener los detalles de los álbumes desde la API y almacenarlos en la base de datos
            val albums = albumIds.map { albumId ->
                NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId)
            }
            albumsDao.insertAll(albums)
            albums
        }
    }
}
