package com.appsmoviles.grupo15.vinilos_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsmoviles.grupo15.vinilos_app.models.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table ORDER BY LOWER(name) ASC")
    suspend fun getCollectors(): List<Collector>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collectors: List<Collector>)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll()
}