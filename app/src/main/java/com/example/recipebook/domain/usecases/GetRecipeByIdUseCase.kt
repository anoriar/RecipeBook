package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {

    fun getRecipeById(id: Int): Recipe{
        return recipeRepository.getRecipeById(id)
    }
}