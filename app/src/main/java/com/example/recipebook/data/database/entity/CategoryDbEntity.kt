package com.example.recipebook.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "categories")
class CategoryDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
}