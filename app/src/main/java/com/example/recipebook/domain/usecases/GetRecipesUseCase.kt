package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import com.example.recipebook.domain.repository.query.RecipeListQuery
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val recipeRepository: RecipeRepositoryInterface) {

    fun getRecipes(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>>{
        return recipeRepository.getRecipeList(recipeListQuery)
    }
}