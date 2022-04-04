package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class DeleteRecipeUseCase(private val recipeRepository: RecipeRepositoryInterface) {
    fun deleteRecipe(recipe: Recipe){
        recipeRepository.deleteRecipe(recipe)
    }
}