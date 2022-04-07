package com.example.recipebook.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "recipes",
    foreignKeys = [ForeignKey(entity = CategoryDbEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.SET_NULL)]
)
class RecipeDbEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(index = true)
    val categoryId: Int,
    val text: String,
    val ingredients: String,
    val portions: Int,
    val image: String,
    val isFavourite: Boolean = false
) {
}