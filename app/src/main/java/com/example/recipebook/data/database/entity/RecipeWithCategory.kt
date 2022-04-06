package com.example.recipebook.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithCategory(
    @Embedded val recipe: RecipeDbEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryDbEntity
)