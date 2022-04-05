package com.example.recipebook.di.modules

import android.app.Application
import androidx.room.Room
import com.example.recipebook.data.database.RecipeDatabase
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.repository.RecipeRepositoryImpl
import com.example.recipebook.domain.repository.RecipeRepositoryInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [AppModule::class])
class DataModule {


    @Provides
    fun provideRecipeDatabase(application: Application): RecipeDatabase{
        return Room.databaseBuilder(application, RecipeDatabase::class.java, RecipeDatabase.DB_NAME).build()
    }

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao{
        return recipeDatabase.getRecipeDao()
    }

    @Provides
    fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepositoryInterface{
        return impl
    }
}