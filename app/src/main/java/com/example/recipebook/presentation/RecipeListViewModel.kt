package com.example.recipebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.query.RecipeListQuery
import com.example.recipebook.domain.usecases.GetCategoriesUseCase
import com.example.recipebook.domain.usecases.GetRecipesUseCase
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {


    private var _recipeListQuery: MutableLiveData<RecipeListQuery> = MutableLiveData<RecipeListQuery>(RecipeListQuery())

    val categoriesLiveData: LiveData<List<Category>> = getCategories()

    val recipeListLiveData: LiveData<List<Recipe>> = Transformations.switchMap(_recipeListQuery) {
        getRecipesByQuery(it)
    }

    private fun getRecipesByQuery(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>>{
       return getRecipesUseCase.getRecipes(recipeListQuery)
    }

    private fun getCategories(): LiveData<List<Category>>{
        return getCategoriesUseCase.getGategories()
    }

    fun searchChange(searchQuery: String){
        _recipeListQuery.value?.search = searchQuery
    }

    fun categorySelect(categoryId: Int){
        _recipeListQuery.value?.categoryIds?.add(categoryId)
    }

    fun categoryUnselect(categoryId: Int){
        _recipeListQuery.value?.categoryIds?.remove(categoryId)
    }
}