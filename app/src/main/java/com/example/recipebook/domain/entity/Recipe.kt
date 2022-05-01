package com.example.recipebook.domain.entity

data class Recipe(
    val name: String,
    val category: Category?,
    val text: String,
    val ingredients: String,
    val portions: Int,
    val image: String?,
    val isFavourite: Boolean = false,
    val id: Int?
) {
}