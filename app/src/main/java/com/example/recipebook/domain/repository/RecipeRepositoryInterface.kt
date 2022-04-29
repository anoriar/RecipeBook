package com.example.recipebook.domain.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.query.RecipeListQuery

interface RecipeRepositoryInterface {
    fun getRecipeList(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>>
    suspend fun getRecipeById(id: Int): Recipe
    suspend fun addRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
}