package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class UpdateRecipeUseCase(private val recipeRepository: RecipeRepositoryInterface) {
    fun updateRecipe(recipe: Recipe){
        recipeRepository.updateRecipe(recipe)
    }
}