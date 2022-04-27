package com.example.recipebook.domain.usecases

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepositoryInterface) {

    fun getCategoriesLiveData(): LiveData<List<Category>>{
       return categoryRepository.getCategoryListLiveData()
    }

    fun getCategories(): List<Category>{
        return categoryRepository.getCategoryList()
    }
}