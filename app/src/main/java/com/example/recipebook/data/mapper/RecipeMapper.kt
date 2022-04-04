package com.example.recipebook.data.mapper

import com.example.categorybook.data.mapper.CategoryMapper
import com.example.recipebook.data.database.entity.RecipeDbEntity
import com.example.recipebook.domain.entity.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) {
    fun mapDomainToDbEntity(recipe: Recipe): RecipeDbEntity{
        return RecipeDbEntity(
            id = recipe.id?: 0,
            name = recipe.name,
            category = categoryMapper.mapDomainToDbEntity(recipe.category),
            text = recipe.text,
            ingredients = recipe.text,
            portions = recipe.portions,
            image = recipe.image,
            isFavourite = recipe.isFavourite
        )
    }

    fun mapDbEntityToDomain(recipeDb: RecipeDbEntity): Recipe{
        return Recipe(
            id = recipeDb.id,
            name = recipeDb.name,
            category = categoryMapper.mapDbEntityToDomain(recipeDb.category),
            text = recipeDb.text,
            ingredients = recipeDb.text,
            portions = recipeDb.portions,
            image = recipeDb.image,
            isFavourite = recipeDb.isFavourite
        )
    }

    fun mapListDbEntityToListDomain(dbEntities: List<RecipeDbEntity>): List<Recipe>{
        return dbEntities.map {
            mapDbEntityToDomain(it)
        }
    }
}