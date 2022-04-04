package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import javax.inject.Inject

class UpdateRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {
    fun updateRecipe(recipe: Recipe){
        recipeRepository.updateRecipe(recipe)
    }
}