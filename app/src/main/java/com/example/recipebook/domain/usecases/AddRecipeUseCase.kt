package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {
    fun addRecipe(recipe: Recipe){
        recipeRepository.addRecipe(recipe)
    }
}