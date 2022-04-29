package com.example.recipebook.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.mapper.RecipeMapper
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import com.example.recipebook.domain.repository.query.RecipeListQuery
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeMapper: RecipeMapper,
    private val recipeDao: RecipeDao
): RecipeRepositoryInterface
{

    override fun getRecipeList(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>> {
        var strQuery = "SELECT * FROM recipes"
        val conditions: MutableList<String> = mutableListOf()
        if(recipeListQuery.search != null){
            conditions.add("LOWER(recipes.name) like LOWER(\"%${recipeListQuery.search}%\")")
        }

        if(recipeListQuery.categoryIds.isNotEmpty()){
            conditions.add("recipes.categoryId IN (${recipeListQuery.categoryIds.joinToString(", ")})")
        }
        if(recipeListQuery.isFavorites) {
            conditions.add("recipes.isFavourite = 1")
        }
        if(conditions.isNotEmpty()){
            strQuery += " where ${conditions.joinToString(" and ")}"
        }
        val query = SimpleSQLiteQuery(strQuery)
        val recipesDb = recipeDao.getRecipes(query)
        return Transformations.map(recipesDb
        ) {
            recipeMapper.mapListDbEntityToListDomain(it)
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe {
        val recipeDb = recipeDao.getRecipeById(id)
        return recipeMapper.mapDbEntityToDomain(recipeDb)
    }

    override suspend fun addRecipe(recipe: Recipe) {
        recipeDao.addUpdateRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.addUpdateRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipeMapper.mapDomainToDbEntity(recipe))
    }
}