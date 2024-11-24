package com.appsmoviles.grupo15.vinilos_app.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.appsmoviles.grupo15.vinilos_app.models.Album
import com.appsmoviles.grupo15.vinilos_app.models.Artist
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.appsmoviles.grupo15.vinilos_app.models.Collector

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://backvynils-q6yc.onrender.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbums(): List<Album> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                try {
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            Album(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description")
                            )
                        )
                    }
                    cont.resume(list)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }


    fun postComment(body: JSONObject, albumId: Int,  onComplete:(resp:JSONObject)->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(postRequest("albums/$albumId/comments",
            body,
            Response.Listener<JSONObject> { response ->
                onComplete(response)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }

    suspend fun getAlbumDetail(albumId: Int): Album = suspendCoroutine { cont ->
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                try {
                    val item = JSONObject(response)
                    val album = Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description")
                    )
                    cont.resume(album)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getArtists(): List<Artist> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                try {
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                birthDate = item.getString("birthDate")
                            )
                        )
                    }
                    cont.resume(list)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getArtistDetail(artistId: Int): Artist = suspendCoroutine { cont ->
        requestQueue.add(getRequest("musicians/$artistId",
            Response.Listener<String> { response ->
                try {
                    val item = JSONObject(response)
                    val artist = Artist(
                        artistId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate")
                    )
                    cont.resume(artist)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getArtistAlbums(artistId: Int): List<Int> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("musicians/$artistId",
            Response.Listener<String> { response ->
                try {
                    val item = JSONObject(response)
                    val albumsArray = item.getJSONArray("albums")
                    val albumIds = mutableListOf<Int>()
                    for (i in 0 until albumsArray.length()) {
                        val album = albumsArray.getJSONObject(i)
                        albumIds.add(album.getInt("id"))
                    }
                    cont.resume(albumIds)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getCollectors(): List<Collector> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                try {
                    val resp = JSONArray(response)
                    val collectors = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        collectors.add(
                            Collector(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                telephone = item.getString("telephone"),
                                email = item.getString("email")
                            )
                        )
                    }
                    cont.resume(collectors)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getCollectorDetail(collectorId: Int): Collector = suspendCoroutine { cont ->
        requestQueue.add(getRequest("collectors/$collectorId",
            Response.Listener<String> { response ->
                try {
                    val item = JSONObject(response)
                    val collector = Collector(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email")
                    )
                    cont.resume(collector)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getCollectorArtists(collectorId: Int): List<Artist> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("collectors/$collectorId/performers",
            Response.Listener<String> { response ->
                try {
                    val resp = JSONArray(response)
                    val artists = mutableListOf<Artist>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        artists.add(
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                birthDate = item.optString("creationDate", "Desconocido")
                            )
                        )
                    }
                    cont.resume(artists)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }


    suspend fun getCollectorAlbums(collectorId: Int): List<Album> = suspendCoroutine { cont ->
        requestQueue.add(getRequest("collectors/$collectorId/albums",
            Response.Listener<String> { response ->
                try {
                    val resp = JSONArray(response)
                    val albums = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val album = item.getJSONObject("album")
                        albums.add(
                            Album(
                                albumId = album.getInt("id"),
                                name = album.getString("name"),
                                cover = album.getString("cover"),
                                releaseDate = album.getString("releaseDate"),
                                description = album.getString("description"),
                                genre = album.getString("genre"),
                                recordLabel = album.getString("recordLabel")
                            )
                        )
                    }
                    cont.resume(albums)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun createAlbum(album: Album): Unit = suspendCoroutine { cont ->
        val body = JSONObject().apply {
            put("name", album.name)
            put("releaseDate", album.releaseDate)
            put("description", album.description)
            put("genre", album.genre)
            put("recordLabel", album.recordLabel)
            put("cover", album.cover)
        }

        requestQueue.add(postRequest("albums", body,
            Response.Listener {
                cont.resume(Unit)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }
        ))
    }


}