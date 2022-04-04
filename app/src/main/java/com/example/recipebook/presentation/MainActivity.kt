package com.example.recipebook.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.recipebook.R
import com.example.recipebook.domain.usecases.GetRecipesUseCase
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var recipeListViewModel: RecipeListViewModel

//    private val component by lazy {
//        DaggerAppComponent.builder().contextModule(ContextModule(applicationContext)).build()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipeListViewModel.recipeListLiveData.observe(this){
            Log.d("RECIPES", it.toString())
        }
    }
}