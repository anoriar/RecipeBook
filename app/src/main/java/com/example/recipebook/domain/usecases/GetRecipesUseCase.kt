package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class GetRecipesUseCase(private val recipeRepository: RecipeRepositoryInterface) {

    fun getRecipes(): LiveData<List<Recipe>>{
        return recipeRepository.getRecipeList()
    }
}