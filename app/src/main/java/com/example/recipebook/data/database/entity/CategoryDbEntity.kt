package com.example.recipebook.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
}