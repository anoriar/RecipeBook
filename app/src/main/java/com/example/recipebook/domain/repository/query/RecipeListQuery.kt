package com.example.recipebook.domain.repository.query

data class RecipeListQuery(
    var search: String? = null,
    var categoryIds: MutableList<Int> = mutableListOf(),
    var isFavorites: Boolean = false
)