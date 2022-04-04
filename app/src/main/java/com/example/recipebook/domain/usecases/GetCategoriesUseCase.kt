package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface

class GetCategoriesUseCase(private val categoryRepository: CategoryRepositoryInterface) {

    fun getGategories(): LiveData<List<Category>>{
       return categoryRepository.getCategoryList()
    }

}