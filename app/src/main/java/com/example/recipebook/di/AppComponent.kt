package com.example.recipebook.di

import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.di.modules.DataModule
import com.example.recipebook.presentation.MainActivity
import com.example.recipebook.presentation.fragment.RecipeAddEditFragment
import com.example.recipebook.presentation.fragment.RecipeDetailFragment
import com.example.recipebook.presentation.fragment.RecipeListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: RecipeListFragment)

    fun inject(fragment: RecipeAddEditFragment)

    fun inject(fragment: RecipeDetailFragment)
}