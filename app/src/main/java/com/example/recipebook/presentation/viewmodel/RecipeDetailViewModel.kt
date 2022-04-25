package com.example.recipebook.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.usecases.GetRecipeByIdUseCase
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
): ViewModel() {


    private var _recipe: MutableLiveData<Recipe?> = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?>
        get() {
            return _recipe
        }

    fun getRecipeById(id: Int){
        _recipe.value = getRecipeByIdUseCase.getRecipeById(id)
    }
}