package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val categoryRepositoryInterface: CategoryRepositoryInterface
) {
    fun getCategoryById(id: Int): Category{
        return categoryRepositoryInterface.getCategoryById(id)
    }
}