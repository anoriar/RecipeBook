package com.example.recipebook.domain.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Category

interface CategoryRepositoryInterface {
    fun getCategoryListLiveData(): LiveData<List<Category>>
    fun getCategoryList(): List<Category>
    fun getCategoryById(id: Int): Category
    fun addCategory(category: Category)
    fun updateCategory(category: Category)
}