package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {

    fun getRecipeById(id: Int): LiveData<Recipe>{
        return recipeRepository.getRecipeById(id)
    }
}