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
    fun getRecipes(query: SupportSQLiteQuery): LiveData<List<RecipeWithCategory>>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeById(id: Int): RecipeWithCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUpdateRecipe(recipe: RecipeDbEntity)

    @Delete
    fun deleteRecipe(recipe: RecipeDbEntity)
}