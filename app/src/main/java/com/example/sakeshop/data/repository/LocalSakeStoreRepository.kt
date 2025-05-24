package com.example.sakeshop.data.repository

import android.content.Context
import android.util.Log
import com.example.sakeshop.domain.model.SakeStore
import com.example.sakeshop.domain.repository.SakeStoreRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonBuilder


class LocalSakeStoreRepository(
    private val context: Context
) : SakeStoreRepository {
    override suspend fun getSakeStores(): Result<List<SakeStore>> = runCatching {
        try {
            context.assets.open("data.json").use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                Log.d("Repository", "JSON lido: $jsonString")
                val json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    coerceInputValues = true
                }
                json.decodeFromString<List<SakeStore>>(jsonString)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Erro ao ler JSON", e)
            throw e
        }
    }
}