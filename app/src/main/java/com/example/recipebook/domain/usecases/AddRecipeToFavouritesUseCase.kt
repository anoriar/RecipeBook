package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import javax.inject.Inject

class AddRecipeToFavouritesUseCase @Inject constructor(
    private val updateRecipeUseCase: UpdateRecipeUseCase
) {
    fun addRecipeToFavourites(recipe: Recipe){
        val newRecipe = recipe.copy(isFavourite = true)
        updateRecipeUseCase.updateRecipe(newRecipe)
    }
}