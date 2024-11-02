package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumDetailRepository(val application: Application) {

    fun getAlbumDetail(albumId: Int, onComplete: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId, onComplete, onError)
    }
}
