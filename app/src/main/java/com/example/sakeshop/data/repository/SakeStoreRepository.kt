package com.example.sakeshop.data.repository

import com.example.sakeshop.domain.model.SakeStore

class SakeStoreRepository {
    fun getSakeStores(): List<SakeStore> = listOf(
        SakeStore(
            id = "1",
            name = "Loja X",
            description = "Loja especializada em sakês importados",
            address = "Rua Aleatória, 123",
            rating = 4f,
            imageUrl = "", // URL placeholder
            websiteUrl = ""
        ),
        SakeStore(
            id = "2",
            name = "Loja Y",
            description = "Maior adega de sakês artesanais",
            address = "Avenida Imaginária, 456",
            rating = 4.5f,
            imageUrl = "", // URL placeholder
            websiteUrl = ""
        ),
        SakeStore(
            id = "3",
            name = "Loja Z",
            description = "Sakês selecionados de produtores exclusivos",
            address = "Praça Inventada, 789",
            rating = 4.2f,
            imageUrl = "", // URL placeholder
            websiteUrl = ""
        )
    )
}