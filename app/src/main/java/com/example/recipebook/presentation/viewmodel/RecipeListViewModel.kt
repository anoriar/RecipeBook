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

    private val categoriesLiveData: LiveData<List<Category>> = getCategories()

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
        _recipeListQuery.value = _recipeListQuery.value?.copy(search = searchQuery)?:RecipeListQuery()
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
        addRecipeToFavouritesUseCase.addRecipeToFavourites(recipe)
    }

    fun deleteRecipeFromFavourites(recipe: Recipe){
        deleteRecipeFromFavouritesUseCase.deleteRecipeToFavourites(recipe)
    }
}