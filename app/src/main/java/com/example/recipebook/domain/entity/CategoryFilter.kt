package com.example.recipebook.domain.entity

/**
 * Фильтр категорий на странице списка
 */
data class CategoryFilter(
    val id: Int,
    val name: String,
    val isSelected: Boolean
)