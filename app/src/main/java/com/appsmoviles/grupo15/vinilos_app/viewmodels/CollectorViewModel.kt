package com.appsmoviles.grupo15.vinilos_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsmoviles.grupo15.vinilos_app.models.Collector
import com.appsmoviles.grupo15.vinilos_app.repositories.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> get() = _collectors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val _networkErrorMessage = MutableLiveData<String?>()
    val networkErrorMessage: LiveData<String?> get() = _networkErrorMessage

    private val collectorRepository = CollectorRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val collectors = withContext(Dispatchers.IO) {
                    collectorRepository.refreshData()
                }
                _collectors.postValue(collectors)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            } catch (e: Exception) {
                _collectors.postValue(emptyList())
                _eventNetworkError.postValue(true)
                _networkErrorMessage.postValue("Error al cargar el listado de coleccionistas, por favor intenta de nuevo.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchCollectors() {
        refreshDataFromNetwork()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun resetNetworkErrorMessage() {
        _networkErrorMessage.value = null
    }
}
