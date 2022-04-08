package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import javax.inject.Inject

class DeleteRecipeFromFavouritesUseCase @Inject constructor(
    private val updateRecipeUseCase: UpdateRecipeUseCase
) {
    fun deleteRecipeToFavourites(recipe: Recipe){
        val newRecipe = recipe.copy(isFavourite = false)
        updateRecipeUseCase.updateRecipe(newRecipe)
    }
}