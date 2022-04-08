package com.example.recipebook.presentation

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
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
): ViewModel() {

    private var _recipe: MutableLiveData<Recipe> = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() {
            return _recipe
        }

    val categoriesLiveData: LiveData<List<Category>> = getCategories()

    private fun getCategories(): LiveData<List<Category>>{
        return getCategoriesUseCase.getGategories()
    }

    fun getRecipeById(id: Int){
        _recipe.value = getRecipeByIdUseCase.getRecipeById(id)
    }

    private fun parseInputData(
        inputName: String,
        inputText: String,
        inputPortions: String,
        inputIngredients: String,
        inputImage: String,
        inputCategory: String
    ): Recipe?{
        var recipe: Recipe? = null
        val name = parseString(inputName)
        val text = parseString(inputText)
        val portions = parseNumber(inputPortions)
        val ingredients = parseString(inputIngredients)
        val image = parseString(inputImage)
        val category = getCategoryByIdUseCase.getCategoryById(parseNumber(inputCategory))
        if(validateInput(name, text, portions, ingredients, image)){
            recipe = Recipe(
                name = name,
                text = text,
                portions = portions,
                ingredients = ingredients,
                image = image,
                category = category,
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
        inputCategory: String
    ){
        val recipe = parseInputData(inputName, inputText, inputPortions, inputIngredients, inputImage, inputCategory)
        if(recipe != null){
            addRecipeUseCase.addRecipe(recipe)
        }
    }

    fun updateRecipe(
        id: Int,
        inputName: String,
        inputText: String,
        inputPortions: String,
        inputIngredients: String,
        inputImage: String,
        inputCategory: String
    ){
        val recipe = parseInputData(inputName, inputText, inputPortions, inputIngredients, inputImage, inputCategory)
        if(recipe != null){
            updateRecipeUseCase.updateRecipe(recipe.copy(id = id))
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
            return false
        }
        return true
    }

    companion object {
        val NAME_IS_EMPTY = "NAME_IS_EMPTY" to R.string.empty_field_error
        val TEXT_IS_EMPTY = "TEXT_IS_EMPTY" to R.string.empty_field_error
        val INGREDIENTS_IS_EMPTY = "INGREDIENTS_IS_EMPTY" to R.string.empty_field_error
        val IMAGE_IS_EMPTY = "IMAGE_IS_EMPTY" to R.string.empty_field_error
        val PORTIONS_INVALID_FORMAT = "PORTIONS_INVALID_FORMAT" to R.string.invalid_format_error
    }
}