package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.appsmoviles.grupo15.vinilos_app.database.VinilosRoomDatabase
import com.appsmoviles.grupo15.vinilos_app.models.Collector
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class CollectorRepository(private val application: Application) {

    private val collectorsDao = VinilosRoomDatabase.getDatabase(application).collectorsDao()

    suspend fun refreshData(): List<Collector> {
        val cachedCollectors = collectorsDao.getCollectors()
        return if (cachedCollectors.isNotEmpty()) {
            cachedCollectors
        } else {
            val collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
            collectorsDao.insertAll(collectors)
            collectors
        }
    }
}