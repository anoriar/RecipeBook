package com.example.recipebook.domain.repository.query

class RecipeListQuery(
    val search: String? = null,
    val categoryIds: List<Int>? = null
)