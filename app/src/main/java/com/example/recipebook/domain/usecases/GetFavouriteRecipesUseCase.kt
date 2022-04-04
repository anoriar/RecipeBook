package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class GetFavouriteRecipesUseCase(private val recipeRepository: RecipeRepositoryInterface) {
    fun getFavouriteRecipes(): LiveData<List<Recipe>>{
        return recipeRepository.getRecipeList()
    }
}