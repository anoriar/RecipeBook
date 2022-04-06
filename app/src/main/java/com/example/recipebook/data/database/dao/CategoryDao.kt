package com.example.recipebook.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipebook.data.database.entity.CategoryDbEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUpdateCategory(category: CategoryDbEntity)

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    fun getCategoryById(id: Int): LiveData<CategoryDbEntity>

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryDbEntity>>
}