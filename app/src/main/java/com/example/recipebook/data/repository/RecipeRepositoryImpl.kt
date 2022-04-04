package com.example.recipebook.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.mapper.RecipeMapper
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface

class RecipeRepositoryImpl(
    private val application: Application,
    private val recipeMapper: RecipeMapper,
    private val recipeDao: RecipeDao): RecipeRepositoryInterface
{

    override fun getRecipeList(): LiveData<List<Recipe>> {
        val recipesDb = recipeDao.getRecipes()
        return Transformations.map(recipesDb
        ) {
            recipeMapper.mapListDbEntityToListDomain(it)
        }
    }

    override fun getRecipeById(id: Int): LiveData<Recipe> {
        val recipeDb = recipeDao.getRecipeById(id)
        return Transformations.map(recipeDb
        ) {
            recipeMapper.mapDbEntityToDomain(it)
        }
    }

    override fun addRecipe(recipe: Recipe) {
        recipeDao.addUpdateRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }

    override fun updateRecipe(recipe: Recipe) {
        recipeDao.addUpdateRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }

    override fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }
}