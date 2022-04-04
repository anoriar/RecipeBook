package com.example.recipebook.domain.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.entity.Category

interface CategoryRepositoryInterface {
    fun getCategoryList(): LiveData<List<Category>>
}