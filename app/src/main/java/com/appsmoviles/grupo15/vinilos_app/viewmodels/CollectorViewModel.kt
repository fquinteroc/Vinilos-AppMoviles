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

    private val collectorRepository = CollectorRepository(application)

    fun fetchCollectors() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val collectors = withContext(Dispatchers.IO) {
                    collectorRepository.refreshData()
                }
                _collectors.postValue(collectors)
            } catch (e: Exception) {
                _collectors.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}