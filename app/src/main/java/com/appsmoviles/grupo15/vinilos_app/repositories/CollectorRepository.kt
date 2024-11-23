package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase
import com.appsmoviles.grupo15.vinilos_app.models.Collector
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class CollectorRepository(private val application: Application) {

    private val collectorsDao = VinilosRoomDatabase.getDatabase(application).collectorsDao()

    suspend fun refreshData(): List<Collector> {
        return try {
            val collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
            collectorsDao.deleteAll()
            collectorsDao.insertAll(collectors)
            collectors
        } catch (e: Exception) {
            val cachedCollectors = collectorsDao.getCollectors()
            if (cachedCollectors.isNotEmpty()) {
                cachedCollectors
            } else {
                throw e
            }
        }
    }
}