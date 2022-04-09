package com.example.recipebook.di

import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.di.modules.DataModule
import com.example.recipebook.presentation.MainActivity
import com.example.recipebook.presentation.fragment.RecipeListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(recipeListFragment: RecipeListFragment)
}