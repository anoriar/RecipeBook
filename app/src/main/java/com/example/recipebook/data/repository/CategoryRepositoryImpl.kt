package com.example.recipebook.data.repository

import com.example.categorybook.data.mapper.CategoryMapper
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryMapper: CategoryMapper,
    private val categoryDao: CategoryDao
): CategoryRepositoryInterface {

    override suspend fun getCategoryList(): List<Category> {
        return categoryMapper.mapListDbEntityToListDomain(categoryDao.getCategories())
    }
}