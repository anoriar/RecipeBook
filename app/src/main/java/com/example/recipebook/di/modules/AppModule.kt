package com.example.recipebook.di.modules

import android.app.Application
import com.example.recipebook.presentation.util.media.ImageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application{
        return application
    }

    @Provides
    @Singleton
    fun providesImageManager(): ImageManager{
        return ImageManager(application)
    }
}