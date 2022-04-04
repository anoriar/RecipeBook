package com.example.recipebook.di.modules

import android.app.Application
import com.example.recipebook.data.database.RecipeDatabase
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.repository.RecipeRepositoryImpl
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule() {

    @Binds
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepositoryInterface

    companion object{
        @Provides
        fun provideRecipeDao(application: Application): RecipeDao {
            return RecipeDatabase.getInstance(application).getRecipeDao()
        }
    }

}