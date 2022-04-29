package com.example.recipebook.domain.repository

import com.example.recipebook.domain.entity.Category

interface CategoryRepositoryInterface {
    suspend fun getCategoryList(): List<Category>
}