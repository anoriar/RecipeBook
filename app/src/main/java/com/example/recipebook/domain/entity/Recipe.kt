package com.example.recipebook.domain.entity

data class Recipe(
    var name: String,
    var category: Category,
    var text: String,
    var ingredients: String,
    var portions: Int,
    var image: String,
    var isFavourite: Boolean = false,
    var id: Int?
) {
}