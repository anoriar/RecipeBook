package com.example.recipebook.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeDbEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val category: CategoryDbEntity,
    val text: String,
    val ingredients: String,
    val portions: Int,
    val image: String,
    val isFavourite: Boolean = false
) {
}