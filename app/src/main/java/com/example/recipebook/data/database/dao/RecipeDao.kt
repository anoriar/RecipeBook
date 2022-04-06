package com.example.recipebook.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipebook.data.database.entity.RecipeDbEntity
import com.example.recipebook.data.database.entity.RecipeWithCategory

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): LiveData<List<RecipeWithCategory>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeById(id: Int): LiveData<RecipeWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUpdateRecipe(recipe: RecipeDbEntity)

    @Delete
    fun deleteRecipe(recipe: RecipeDbEntity)
}