package com.example.categorybook.data.mapper

import com.example.recipebook.data.database.entity.CategoryDbEntity
import com.example.recipebook.domain.entity.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

    fun mapDomainToDbEntity(category: Category): CategoryDbEntity {
        return CategoryDbEntity(
            id = category.id?: 0,
            name = category.name
        )
    }

    fun mapDbEntityToDomain(categoryDb: CategoryDbEntity): Category{
        return Category(
            id = categoryDb.id,
            name = categoryDb.name
        )
    }

    fun mapListDbEntityToListDomain(dbEntities: List<CategoryDbEntity>): List<Category>{
        return dbEntities.map {
            mapDbEntityToDomain(it)
        }
    }
}