package com.example.sakeshop.domain.model

data class SakeStore(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val rating: Float,
    val imageUrl: String,
    val websiteUrl: String,
) {
    init {
        require(name.isNotBlank()) { "Nome da loja não pode ser vazio" }
        require(rating in 0.0..5.0) { "Avaliação deve estar entre 0 e 5" }
    }

    fun hasWebsite(): Boolean = websiteUrl.isNotBlank()
}