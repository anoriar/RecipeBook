package com.example.recipebook.domain.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.query.RecipeListQuery

interface RecipeRepositoryInterface {
    fun getRecipeList(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>>
    fun getRecipeById(id: Int): Recipe
    fun addRecipe(recipe: Recipe)
    fun updateRecipe(recipe: Recipe)
    fun deleteRecipe(recipe: Recipe)
}