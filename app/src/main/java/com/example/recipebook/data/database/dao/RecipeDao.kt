package com.example.recipebook.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipebook.data.database.entity.RecipeDbEntity

interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): LiveData<List<RecipeDbEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeById(id: Int): LiveData<RecipeDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUpdateRecipe(recipe: RecipeDbEntity)

    @Delete
    fun deleteRecipe(recipe: RecipeDbEntity)
}