package com.mobiquity.miniapp.model.entities

data class Catalog(
    val categories: List<Category>
)

data class Category(
    val id: String,
    val name: String,
    val description: String,
    val products: List<Product>
)