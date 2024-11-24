package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.models.Collector
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class CollectorDetailRepository(private val application: Application) {

    private val collectorsDao = VinilosRoomDatabase.getDatabase(application).collectorsDao()

    suspend fun getCollectorDetail(collectorId: Int): Collector {
        // Consultar primero en la base de datos local
        val cachedCollector = collectorsDao.getCollectorById(collectorId)
        return cachedCollector ?: run {
            // Si no está en caché, hacer solicitud de red y almacenar en Room
            val collector = NetworkServiceAdapter.getInstance(application).getCollectorDetail(collectorId)
            collectorsDao.insertCollector(collector)
            collector
        }
    }

    suspend fun getCollectorArtists(collectorId: Int): List<Artist> {
        return NetworkServiceAdapter.getInstance(application).getCollectorArtists(collectorId)
    }

    suspend fun getCollectorAlbums(collectorId: Int): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getCollectorAlbums(collectorId)
    }

}