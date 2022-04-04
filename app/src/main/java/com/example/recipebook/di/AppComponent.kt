package com.example.recipebook.di

import com.example.recipebook.di.modules.DataModule
import com.example.recipebook.presentation.MainActivity
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}