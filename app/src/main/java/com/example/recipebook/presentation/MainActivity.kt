package com.example.recipebook.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.recipebook.R
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var recipeListViewModel: RecipeListViewModel

    @Inject
    lateinit var recipeViewModel: RecipeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.builder().appModule(AppModule(application)).build().inject(this)


//        recipeListViewModel.recipeListLiveData.observe(this){
//            Log.d("RECIPES", it.toString())
//        }
//
//        recipeListViewModel.categoriesLiveData.observe(this){
//            Log.d("CATEGORIES", it.toString())
//        }

//        recipeListViewModel.categorySelect(2)
//        recipeListViewModel.searchChange("запеч")

    }
}