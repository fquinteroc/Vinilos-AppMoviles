package com.appsmoviles.grupo15.vinilos_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsmoviles.grupo15.vinilos_app.models.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists_table ORDER BY LOWER(name) ASC")
    suspend fun getArtists(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artists: List<Artist>)

    @Query("DELETE FROM artists_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM artists_table WHERE artistId = :artistId")
    suspend fun getArtistDetail(artistId: Int): Artist?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: Artist)
}