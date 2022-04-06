package com.example.recipebook.data.mapper

import com.example.categorybook.data.mapper.CategoryMapper
import com.example.recipebook.data.database.entity.RecipeDbEntity
import com.example.recipebook.data.database.entity.RecipeWithCategory
import com.example.recipebook.domain.entity.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) {
    fun mapDomainToDbEntity(recipe: Recipe): RecipeDbEntity{
        return RecipeDbEntity(
            id = recipe.id?: 0,
            categoryId = recipe.category?.id?:0,
            name = recipe.name,
            text = recipe.text,
            ingredients = recipe.text,
            portions = recipe.portions,
            image = recipe.image,
            isFavourite = recipe.isFavourite
        )
    }

    fun mapDbEntityToDomain(recipeWithCategory: RecipeWithCategory): Recipe{
        val category = categoryMapper.mapDbEntityToDomain(recipeWithCategory.category)
        return Recipe(
            id = recipeWithCategory.recipe.id,
            name = recipeWithCategory.recipe.name,
            category = category,
            text = recipeWithCategory.recipe.text,
            ingredients = recipeWithCategory.recipe.text,
            portions = recipeWithCategory.recipe.portions,
            image = recipeWithCategory.recipe.image,
            isFavourite = recipeWithCategory.recipe.isFavourite
        )
    }

    fun mapListDbEntityToListDomain(dbEntities: List<RecipeWithCategory>): List<Recipe>{
        return dbEntities.map {
            mapDbEntityToDomain(it)
        }
    }
}