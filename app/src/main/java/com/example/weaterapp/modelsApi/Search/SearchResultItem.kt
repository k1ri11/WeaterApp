package com.example.weaterapp.modelsApi.Search

data class SearchResultItem(
    val name: String,
    val country: String,
    val state: String?,
    val lat: Double,
    val lon: Double
)