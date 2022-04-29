package com.example.recipebook.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.recipebook.data.database.entity.RecipeDbEntity
import com.example.recipebook.data.database.entity.RecipeWithCategory

@Dao
interface RecipeDao {
    @Transaction
    @RawQuery
    fun getRecipes(query: SupportSQLiteQuery): List<RecipeWithCategory>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getRecipeById(id: Int): RecipeWithCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateRecipe(recipe: RecipeDbEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeDbEntity)
}