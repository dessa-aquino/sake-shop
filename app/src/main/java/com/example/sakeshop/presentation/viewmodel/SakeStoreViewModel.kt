package com.example.sakeshop.presentation.viewmodel

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
    private val _uiState = MutableStateFlow<SakeStoreUiState>(SakeStoreUiState.Loading)
    val uiState: StateFlow<SakeStoreUiState> = _uiState.asStateFlow()

    init {
        loadSakeStores()
    }

    private fun loadSakeStores() {
        viewModelScope.launch {
            _uiState.value = SakeStoreUiState.Loading
            repository.getSakeStores()
                .onSuccess { shops: List<SakeStore> ->
                    _uiState.value = SakeStoreUiState.Success(shops)
                }
                .onFailure { error ->
                    _uiState.value = SakeStoreUiState.Error(error.message ?: "Erro desconhecido")
                }
        }
    }

    fun getStoreUrl(): String {
        return when (val currentState = uiState.value) {
            is SakeStoreUiState.Success -> {
                // Assumindo que estamos trabalhando com a loja selecionada atualmente
                currentState.stores.firstOrNull()?.website ?: ""
            }
            else -> ""
        }

    }

    fun getStoreDetails(storeName: String): SakeStore {
        return when (val currentState = uiState.value) {
            is SakeStoreUiState.Success -> {
                currentState.stores.find { it.name == storeName }
                    ?: throw IllegalArgumentException("Loja não encontrada com o nome: $storeName")
            }
            is SakeStoreUiState.Loading -> {
                throw IllegalStateException("Os dados das lojas ainda estão sendo carregados")
            }
            is SakeStoreUiState.Error -> {
                throw IllegalStateException("Erro ao buscar dados da loja: ${currentState.message}")
            }
        }
    }

}

sealed class SakeStoreUiState {
    data object Loading : SakeStoreUiState()
    data class Success(val stores: List<SakeStore>) : SakeStoreUiState()
    data class Error(val message: String) : SakeStoreUiState()
}