package com.example.sakeshop.data.repository

import android.content.Context
import android.util.Log
import com.example.sakeshop.domain.model.SakeStore
import com.example.sakeshop.domain.repository.SakeStoreRepository
import kotlinx.serialization.json.Json

object LocalData {
    const val ORIGINAL_DATA = "data.json"
    const val ERROR_DATA = "data_error.json"

    var currentFileName = LocalData.ORIGINAL_DATA
}

class LocalSakeStoreRepository(
    private val context: Context,
    private val json: Json = defaultJson()
) : SakeStoreRepository {

    private var cachedStores: List<SakeStore>? = null

    companion object {


        private fun defaultJson() = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }
    }

    override suspend fun clearCache() {
        cachedStores = null
    }

    override suspend fun getSakeStores(): Result<List<SakeStore>> = runCatching {
        cachedStores ?: run {
            val stores = loadStoresFromAssets()
            cachedStores = stores
            stores
        }
    }.onFailure { error ->
        Log.e("LocalSakeStoreRepository", "Error loading stores json", error)
    }

    private fun loadStoresFromAssets(): List<SakeStore> {
        context.assets.open(LocalData.currentFileName).use { inputStream ->
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            require(jsonString.isNotBlank()) { "Json file is empty or blank" }

            return json.decodeFromString<List<SakeStore>>(jsonString)
        }
    }
}