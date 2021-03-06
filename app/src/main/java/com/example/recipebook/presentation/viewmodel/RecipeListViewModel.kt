package com.example.recipebook.presentation.viewmodel

import androidx.lifecycle.*
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.CategoryFilter
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.repository.query.RecipeListQuery
import com.example.recipebook.domain.usecases.AddRecipeToFavouritesUseCase
import com.example.recipebook.domain.usecases.DeleteRecipeFromFavouritesUseCase
import com.example.recipebook.domain.usecases.GetCategoriesUseCase
import com.example.recipebook.domain.usecases.GetRecipesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addRecipeToFavouritesUseCase: AddRecipeToFavouritesUseCase,
    private val deleteRecipeFromFavouritesUseCase: DeleteRecipeFromFavouritesUseCase
): ViewModel() {

    private var _recipeListQuery: MutableLiveData<RecipeListQuery> = MutableLiveData<RecipeListQuery>(
        RecipeListQuery()
    )
    val recipeListQuery: LiveData<RecipeListQuery>
        get() {
            return _recipeListQuery
        }

    private val categoriesLiveData: LiveData<List<Category>> = liveData {
        emit(getCategoriesUseCase.getCategories())
    }

    val categoriesFilter: MediatorLiveData<List<CategoryFilter>> = MediatorLiveData()
    init {
        categoriesFilter.addSource(categoriesLiveData) {
            buildCategoriesFilter()
        }
        categoriesFilter.addSource(_recipeListQuery) {
            if(categoriesFilter.value != null){
                buildCategoriesFilter()
            }
        }
    }

    val recipeListLiveData: LiveData<List<Recipe>> = Transformations.switchMap(_recipeListQuery) {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(getRecipesByQuery(it))
        }
    }

    private fun buildCategoriesFilter(){
        categoriesFilter.value = categoriesLiveData.value?.map { category ->
            var isSelected = false
            _recipeListQuery.value?.let {
                if(it.categoryIds.contains(category.id)){
                    isSelected = true
                }
            }
            CategoryFilter(category.id?:0, category.name, isSelected)
        }
    }

    private fun getRecipesByQuery(recipeListQuery: RecipeListQuery): List<Recipe>{
       return getRecipesUseCase.getRecipes(recipeListQuery)
    }


    fun changeSearchQuery(searchQuery: String){
        _recipeListQuery.value = _recipeListQuery.value?.copy(search = searchQuery)?:RecipeListQuery()
    }

    fun changeIsFavorites(isFavorites: Boolean){
        _recipeListQuery.value = _recipeListQuery.value?.copy(isFavorites = isFavorites)?:RecipeListQuery()
    }

    fun selectCategory(categoryId: Int){
        val curQuery: RecipeListQuery = _recipeListQuery.value?: RecipeListQuery()
        curQuery.categoryIds.add(categoryId)
        _recipeListQuery.value = curQuery.copy()
    }

    fun unselectCategory(categoryId: Int){
        val curQuery: RecipeListQuery = _recipeListQuery.value?: RecipeListQuery()
        curQuery.categoryIds.remove(categoryId)
        _recipeListQuery.value = curQuery.copy()
    }

    fun addRecipeToFavourites(recipe: Recipe){
        viewModelScope.launch {
            addRecipeToFavouritesUseCase.addRecipeToFavourites(recipe)
        }
    }

    fun deleteRecipeFromFavourites(recipe: Recipe){
        viewModelScope.launch {
            deleteRecipeFromFavouritesUseCase.deleteRecipeToFavourites(recipe)
        }
    }
}