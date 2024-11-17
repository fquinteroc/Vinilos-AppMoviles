package com.appsmoviles.grupo15.vinilos_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appsmoviles.grupo15.vinilos_app.database.dao.AlbumsDao
import com.appsmoviles.grupo15.vinilos_app.database.dao.ArtistsDao
import com.appsmoviles.grupo15.vinilos_app.database.dao.CollectorsDao
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import com.appsmoviles.grupo15.vinilos_app.models.Collector

@Database(entities = [Album::class, Artist::class, Collector::class], version = 3, exportSchema = false)
abstract class VinilosRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao
    abstract fun artistsDao(): ArtistsDao
    abstract fun collectorsDao(): CollectorsDao

    companion object {
        @Volatile
        private var INSTANCE: VinilosRoomDatabase? = null

        fun getDatabase(context: Context): VinilosRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinilosRoomDatabase::class.java,
                    "vinyls_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}