package com.example.sakeshop.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class SakeStore(
    val name: String,
    val description: String,
    val picture: String?,
    val rating: Double,
    val address: String,
    val coordinates: List<Double>,
    @SerialName("google_maps_link")
    val googleMapsLink: String,
    val website: String
) {
    init {
        require(name.isNotBlank()) { "Nome da loja não pode ser vazio" }
        require(rating in 0.0..5.0) { "Avaliação deve estar entre 0 e 5" }
    }
}