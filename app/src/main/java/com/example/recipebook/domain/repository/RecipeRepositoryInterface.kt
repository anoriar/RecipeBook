package com.example.recipebook.domain.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe

interface RecipeRepositoryInterface {
    fun getRecipeList(): LiveData<List<Recipe>>
    fun getRecipeById(id: Int): LiveData<Recipe>
    fun addRecipe(recipe: Recipe)
    fun updateRecipe(recipe: Recipe)
    fun deleteRecipe(recipe: Recipe)
}