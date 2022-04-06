package com.example.recipebook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.database.entity.CategoryDbEntity
import com.example.recipebook.data.database.entity.RecipeDbEntity

@Database(entities = [RecipeDbEntity::class, CategoryDbEntity::class], version = 1)
abstract class RecipeDatabase: RoomDatabase () {

    abstract fun getRecipeDao():RecipeDao

    abstract fun getCategoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "recipes.db"
    }
}