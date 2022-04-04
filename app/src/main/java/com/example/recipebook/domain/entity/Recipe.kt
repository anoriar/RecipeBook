package com.example.recipebook.domain.entity

data class Recipe(
    var category: Category,
    var cooking: String,
    var ingredients: String,
    var portions: Int,
    var image: String,
    var id: Int?
) {
}