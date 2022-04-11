package com.example.recipebook.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.CategoryFilter
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.query.RecipeListQuery
import com.example.recipebook.domain.usecases.AddRecipeToFavouritesUseCase
import com.example.recipebook.domain.usecases.DeleteRecipeFromFavouritesUseCase
import com.example.recipebook.domain.usecases.GetCategoriesUseCase
import com.example.recipebook.domain.usecases.GetRecipesUseCase
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addRecipeToFavouritesUseCase: AddRecipeToFavouritesUseCase,
    private val deleteRecipeFromFavouritesUseCase: DeleteRecipeFromFavouritesUseCase
): ViewModel() {

    private var _recipeListQuery: MutableLiveData<RecipeListQuery> = MutableLiveData<RecipeListQuery>(RecipeListQuery())


    private val categoriesLiveData: LiveData<List<Category>> = getCategories()

    val categoriesFilter: LiveData<List<CategoryFilter>> = Transformations.map(categoriesLiveData) { it ->
        it.map { category ->
            var isSelected = false
            _recipeListQuery.value?.let {
                if(it.categoryIds.contains(category.id)){
                    isSelected = true
                }
            }

            CategoryFilter(category.id?:0, category.name, isSelected)
        }
    }


    val recipeListLiveData: LiveData<List<Recipe>> = Transformations.switchMap(_recipeListQuery) {
        getRecipesByQuery(it)
    }

    private fun getRecipesByQuery(recipeListQuery: RecipeListQuery): LiveData<List<Recipe>>{
       return getRecipesUseCase.getRecipes(recipeListQuery)
    }

    private fun getCategories(): LiveData<List<Category>>{
        return getCategoriesUseCase.getGategories()
    }

    fun changeSearchQuery(searchQuery: String){
        _recipeListQuery.value?.search = searchQuery
    }

    fun selectCategory(categoryId: Int){
        _recipeListQuery.value?.categoryIds?.add(categoryId)
    }

    fun unselectCategory(categoryId: Int){
        _recipeListQuery.value?.categoryIds?.remove(categoryId)
    }

    fun addRecipeToFavourites(recipe: Recipe){
        addRecipeToFavouritesUseCase.addRecipeToFavourites(recipe)
    }

    fun deleteRecipeFromFavourites(recipe: Recipe){
        deleteRecipeFromFavouritesUseCase.deleteRecipeToFavourites(recipe)
    }
}