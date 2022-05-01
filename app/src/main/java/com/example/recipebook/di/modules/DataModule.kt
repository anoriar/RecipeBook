package com.example.recipebook.di.modules

import android.app.Application
import androidx.room.Room
import com.example.recipebook.data.database.MIGRATION_1_2
import com.example.recipebook.data.database.RecipeDatabase
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.repository.CategoryRepositoryImpl
import com.example.recipebook.data.repository.RecipeRepositoryImpl
import com.example.recipebook.domain.repository.CategoryRepositoryInterface
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import dagger.Module
import dagger.Provides


@Module(includes = [AppModule::class])
class DataModule {


    @Provides
    fun provideRecipeDatabase(application: Application): RecipeDatabase{
        return Room.databaseBuilder(application, RecipeDatabase::class.java, RecipeDatabase.DB_NAME)
            .createFromAsset("database/recipes.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao{
        return recipeDatabase.getRecipeDao()
    }

    @Provides
    fun provideCategoryDao(recipeDatabase: RecipeDatabase): CategoryDao {
        return recipeDatabase.getCategoryDao()
    }

    @Provides
    fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepositoryInterface{
        return impl
    }


    @Provides
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepositoryInterface {
        return impl
    }
}