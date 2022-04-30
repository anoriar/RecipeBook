package com.example.recipebook.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import com.example.recipebook.R
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.usecases.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
): ViewModel() {

    private var _recipe: MutableLiveData<Recipe> = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() {
            return _recipe
        }

    private var _recipeImage: MutableLiveData<Uri?> = MutableLiveData<Uri?>()
    val recipeImage: LiveData<Uri?>
        get() {
            return _recipeImage
        }

    val categoriesLiveData: LiveData<List<Category>> = liveData {
        emit(getCategoriesUseCase.getCategories())
    }

    val categorySpinnerPairData: LiveData<Category> =
        object: MediatorLiveData<Category>() {
            var recipe: Recipe? = null
            var categories: List<Category>? = null
            init {
                addSource(_recipe) { recipe ->
                    this.recipe = recipe
                    categories?.let {
                        if(it.contains(recipe.category)){
                            value = recipe.category
                        }
                    }
                }
                addSource(categoriesLiveData) { categories ->
                    this.categories = categories
                    recipe?.let {  if(categories.contains(it.category)){
                        value = it.category
                    } }
                }
            }
        }

    private var _errors: MutableLiveData<MutableList<Pair<String, Int>>> = MutableLiveData()
    val errors: MutableLiveData<MutableList<Pair<String, Int>>>
        get() {
            return _errors
        }

    private val _shouldClose: MutableLiveData<Unit> = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit>
        get() = _shouldClose


    fun initRecipeById(id: Int){
        viewModelScope.launch {
            _recipe.value = getRecipeByIdUseCase.getRecipeById(id)
        }
    }

    fun setImageUri(imageUri: Uri){
        _recipeImage.value = imageUri
    }

    private fun parseInputData(
        inputName: String,
        inputText: String,
        inputPortions: String,
        inputIngredients: String,
        inputImage: String,
        inputCategory: Category
    ): Recipe?{
        var recipe: Recipe? = null
        val name = parseString(inputName)
        val text = parseString(inputText)
        val portions = parseNumber(inputPortions)
        val ingredients = parseString(inputIngredients)
        val image = parseString(inputImage)
        if(validateInput(name, text, portions, ingredients, image)){
            recipe = Recipe(
                name = name,
                text = text,
                portions = portions,
                ingredients = ingredients,
                image = image,
                category = inputCategory,
                id = null
            )
        }
        return recipe
    }

    fun addRecipe(
        inputName: String,
        inputText: String,
        inputPortions: String,
        inputIngredients: String,
        inputImage: String,
        inputCategory: Category
    ){
        val recipe = parseInputData(inputName, inputText, inputPortions, inputIngredients, inputImage, inputCategory)
        if(recipe != null){
            viewModelScope.launch {
                addRecipeUseCase.addRecipe(recipe)
                finishWork()
            }
        }
    }

    fun updateRecipe(
        id: Int,
        inputName: String,
        inputText: String,
        inputPortions: String,
        inputIngredients: String,
        inputImage: String,
        inputCategory: Category
    ){
        val recipe = parseInputData(inputName, inputText, inputPortions, inputIngredients, inputImage, inputCategory)
        if(recipe != null){
            viewModelScope.launch {
                updateRecipeUseCase.updateRecipe(recipe.copy(id = id))
            }
            finishWork()
        }
    }

    fun deleteRecipe(){
        viewModelScope.launch {
            _recipe.value?.let {
                deleteRecipeUseCase.deleteRecipe(it)
            }
        }
    }

    private fun parseString(str: String): String{
        return str.trim() ?: ""
    }

    private fun parseNumber(strNum: String): Int{
        return try {
            strNum.trim().toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(inputName: String, inputText: String, inputPortions: Int, inputIngredients: String, inputImage: String): Boolean{
        val errors: MutableList<Pair<String, Int>> = mutableListOf()
        if(inputName.isEmpty()){
            errors.add(NAME_IS_EMPTY)
        }

        if(inputText.isEmpty()){
            errors.add(TEXT_IS_EMPTY)
        }
        if(inputPortions <= 0 ){
            errors.add(PORTIONS_INVALID_FORMAT)
        }
        if(inputIngredients.isEmpty()){
            errors.add(INGREDIENTS_IS_EMPTY)
        }
        if(errors.isNotEmpty()){
            _errors.value = errors
            return false
        }
        return true
    }

    fun finishWork() {
        _shouldClose.value = Unit
    }


    companion object {
        val NAME_IS_EMPTY = "NAME_IS_EMPTY" to R.string.empty_field_error
        val TEXT_IS_EMPTY = "TEXT_IS_EMPTY" to R.string.empty_field_error
        val INGREDIENTS_IS_EMPTY = "INGREDIENTS_IS_EMPTY" to R.string.empty_field_error
        val IMAGE_IS_EMPTY = "IMAGE_IS_EMPTY" to R.string.empty_field_error
        val PORTIONS_INVALID_FORMAT = "PORTIONS_INVALID_FORMAT" to R.string.invalid_format_error
    }
}