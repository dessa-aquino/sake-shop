package com.example.sakeshop.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sakeshop.domain.model.SakeStore
import com.example.sakeshop.domain.repository.SakeStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SakeStoreViewModel(
    private val repository: SakeStoreRepository
) : ViewModel() {
    private val _selectedStore = MutableStateFlow<SakeStore?>(null)
    val selectedStore: StateFlow<SakeStore?> = _selectedStore.asStateFlow()

    private val _uiState = MutableStateFlow<SakeStoreUiState>(SakeStoreUiState.Loading)
    val uiState: StateFlow<SakeStoreUiState> = _uiState.asStateFlow()

    init {
        loadSakeStores()
    }

    private fun loadSakeStores() {
        viewModelScope.launch {
            try {
                _uiState.value = SakeStoreUiState.Loading
                val result = repository.getSakeStores()
                result.fold(
                    onSuccess = { stores ->
                        _uiState.value = if (stores.isEmpty())
                            SakeStoreUiState.Error("No store found")
                        else
                            SakeStoreUiState.Success(stores)
                    },
                    onFailure = { error ->
                        _uiState.value = SakeStoreUiState.Error(
                            error.message ?: "Error loading data store"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = SakeStoreUiState.Error(
                    "Unnexpected error: ${e.localizedMessage}"
                )
            }
        }
    }

    fun loadStoreDetails(storeName: String) {
        viewModelScope.launch {
            val state = uiState.value
            if (state is SakeStoreUiState.Success) {
                _selectedStore.value = state.stores.find { it.name == storeName }
                    ?: run {
                        Log.e("LoadStore", "Store not found: $storeName")
                        null
                    }
            }
        }
    }
}

sealed class SakeStoreUiState {
    data object Loading : SakeStoreUiState()
    data class Success(val stores: List<SakeStore>) : SakeStoreUiState()
    data class Error(val message: String) : SakeStoreUiState()
}