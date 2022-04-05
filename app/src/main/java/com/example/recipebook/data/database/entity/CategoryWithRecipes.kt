package com.example.recipebook.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithRecipes(
    @Embedded val category: CategoryDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val recipes: List<RecipeDbEntity>
) {
}