package com.example.recipebook.domain.usecases

import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val categoryRepositoryInterface: CategoryRepositoryInterface
) {
    fun addCategory(category: Category){
        categoryRepositoryInterface.addCategory(category)
    }
}