package com.example.recipebook.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.categorybook.data.mapper.CategoryMapper
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryMapper: CategoryMapper,
    private val categoryDao: CategoryDao
): CategoryRepositoryInterface {

    override fun getCategoryList(): LiveData<List<Category>> {
        return Transformations.map(categoryDao.getCategories()){
            categoryMapper.mapListDbEntityToListDomain(it)
        }
    }

    override fun addCategory(category: Category) {
        categoryDao.addUpdateCategory(categoryMapper.mapDomainToDbEntity(category))
    }

    override fun updateCategory(category: Category) {
        categoryDao.addUpdateCategory(categoryMapper.mapDomainToDbEntity(category))
    }
}