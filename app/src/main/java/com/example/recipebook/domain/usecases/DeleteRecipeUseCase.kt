package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {
    fun deleteRecipe(recipe: Recipe){
        recipeRepository.deleteRecipe(recipe)
    }
}