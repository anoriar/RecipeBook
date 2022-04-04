package com.example.recipebook.data.mapper

import com.example.categorybook.data.mapper.CategoryMapper
import com.example.recipebook.data.database.entity.RecipeDbEntity
import com.example.recipebook.domain.entity.Recipe

class RecipeMapper(
    private val categoryMapper: CategoryMapper = CategoryMapper()
) {
    fun mapDomainToDbEntity(recipe: Recipe): RecipeDbEntity{
        return RecipeDbEntity(
            id = recipe.id?: 0,
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
            id = recipeDb.id?: 0,
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