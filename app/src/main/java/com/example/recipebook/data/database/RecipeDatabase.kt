package com.example.recipebook.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.database.entity.CategoryDbEntity
import com.example.recipebook.data.database.entity.RecipeDbEntity

@Database(entities = [RecipeDbEntity::class, CategoryDbEntity::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun getRecipeDao():RecipeDao

    abstract fun getCategoryDao(): CategoryDao

    companion object {
        private var instance: RecipeDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "recipes.db"

        fun getInstance(context: Context): RecipeDatabase {
            instance?.let {
                return it
            }
            synchronized(lock) {
//                Двойная проверка на instance, чтобы при входе у потоков не добавлялись новые значения инстансов
                instance?.let {
                    return it
                }

                val appDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java, DB_NAME
                ).build()
                instance = appDatabase
                return appDatabase
            }
        }
    }
}