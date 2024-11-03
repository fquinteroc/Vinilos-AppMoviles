package com.appsmoviles.grupo15.vinilos_app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {

    fun refreshData(onComplete: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            onComplete(it)
        },
            onError
        )
    }
}
