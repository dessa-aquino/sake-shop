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
        require(name.isNotBlank()) { "Store name cannot be empty" }
        require(description.isNotBlank()) { "Store description cannot be empty" }
        require(rating in 0.0..5.0) { "Rating must be between 0 and 5" }
        require(address.isNotBlank()) { "Store description cannot be empty" }
        require(coordinates.size == 2) { "Coordinates must contain latitude and longitude" }
        require(googleMapsLink.startsWith("https://")) { "Google Maps link must be a valid URL" }
        require(website.startsWith("https://") || website.startsWith("http://"))
        { "Website link must be a valid URL" }
    }
}