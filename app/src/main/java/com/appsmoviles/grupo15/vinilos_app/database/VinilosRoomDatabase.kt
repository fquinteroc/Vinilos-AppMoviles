package com.appsmoviles.grupo15.vinilos_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appsmoviles.grupo15.vinilos_app.database.dao.AlbumsDao
import com.appsmoviles.grupo15.vinilos_app.models.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class VinilosRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

    companion object {
        @Volatile
        private var INSTANCE: VinilosRoomDatabase? = null

        fun getDatabase(context: Context): VinilosRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinilosRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}