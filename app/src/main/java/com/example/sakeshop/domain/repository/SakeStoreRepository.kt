package com.example.sakeshop.domain.repository

import com.example.sakeshop.domain.model.SakeStore


interface SakeStoreRepository {
    suspend fun getSakeStores(): Result<List<SakeStore>>
}
