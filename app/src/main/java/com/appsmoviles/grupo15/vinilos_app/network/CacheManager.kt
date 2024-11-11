package com.appsmoviles.grupo15.vinilos_app.network

import android.content.Context
import com.appsmoviles.grupo15.vinilos_app.models.Album

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also { instance = it }
            }
    }

    private val cacheDirectory = context.cacheDir

    // Caché para el detalle del álbum
    private var albumDetails: HashMap<Int, Album> = hashMapOf()

    // Caché para el listado de álbumes
    private var albumListCache: List<Album>? = null

    fun addAlbumDetail(albumId: Int, album: Album) {
        if (!albumDetails.containsKey(albumId)) {
            albumDetails[albumId] = album
        }
    }

    fun getAlbumDetail(albumId: Int): Album? {
        return albumDetails[albumId]
    }

    fun addAlbumList(albums: List<Album>) {
        albumListCache = albums
    }

    fun getAlbumList(): List<Album>? {
        return albumListCache
    }
}
