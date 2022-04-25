package com.example.recipebook.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.usecases.*
import javax.inject.Inject

class RecipeViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
): ViewModel() {

    private var _recipeName: MutableLiveData<String?> = MutableLiveData<String?>()
    val recipeName: LiveData<String?>
        get() {
            return _recipeName
        }

    private var _recipeText: MutableLiveData<String?> = MutableLiveData<String?>()
    val recipeText: LiveData<String?>
        get() {
            return _recipeText
        }

    private var _recipeIngredients: MutableLiveData<String?> = MutableLiveData<String?>()
    val recipeIngredients: LiveData<String?>
        get() {
            return _recipeIngredients
        }

    private var _recipeCategory: MutableLiveData<Category?> = MutableLiveData<Category?>()
    val recipeCategory: LiveData<Category?>
        get() {
            return _recipeCategory
        }

    private var _recipePortions: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val recipePortions: LiveData<Int>
        get() {
            return _recipePortions
        }


    private var _recipeImage: MutableLiveData<Uri?> = MutableLiveData<Uri?>()
    val recipeImage: LiveData<Uri?>
        get() {
            return _recipeImage
        }

    val categoriesLiveData: LiveData<List<Category>> = getCategories()

    private var _selectedCategoryId: MutableLiveData<Int> = MutableLiveData()
    val selectedCategoryId: MutableLiveData<Int>
        get() {
            return _selectedCategoryId
        }

    private var _errors: MutableLiveData<MutableList<Pair<String, Int>>> = MutableLiveData()
    val errors: MutableLiveData<MutableList<Pair<String, Int>>>
        get() {
            return _errors
        }


    private val _shouldClose: MutableLiveData<Unit> = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit>
        get() = _shouldClose

    private fun getCategories(): LiveData<List<Category>>{
        return getCategoriesUseCase.getGategories()
    }

    fun getRecipeById(id: Int){
//        TODO: заполнить поля вью модели
//        _recipe.value = getRecipeByIdUseCase.getRecipeById(id)
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
        val image = parseString(recipeImage.value.toString())
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
            addRecipeUseCase.addRecipe(recipe)
            finishWork()
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
            updateRecipeUseCase.updateRecipe(recipe.copy(id = id))
            finishWork()
        }
    }

    fun deleteRecipe(recipe: Recipe){
        deleteRecipeUseCase.deleteRecipe(recipe)
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
        if(inputImage.isEmpty()){
            errors.add(IMAGE_IS_EMPTY)
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