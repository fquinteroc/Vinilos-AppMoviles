package com.appsmoviles.grupo15.vinilos_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsmoviles.grupo15.vinilos_app.models.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table ORDER BY LOWER(name) ASC")
    suspend fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM albums_table WHERE albumId = :albumId")
    suspend fun getAlbumDetail(albumId: Int): Album?
}