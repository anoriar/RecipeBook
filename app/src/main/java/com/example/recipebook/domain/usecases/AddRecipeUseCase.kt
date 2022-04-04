package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class AddRecipeUseCase(private val recipeRepository: RecipeRepositoryInterface) {
    fun addRecipe(recipe: Recipe){
        recipeRepository.addRecipe(recipe)
    }
}