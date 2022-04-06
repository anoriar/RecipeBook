package com.example.recipebook.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.domain.usecases.AddCategoryUseCase
import com.example.recipebook.domain.usecases.AddRecipeUseCase
import com.example.recipebook.domain.usecases.GetRecipesUseCase
import java.lang.Exception
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
//    TODO: убрать
    private val addCategoryUseCase: AddCategoryUseCase
): ViewModel() {

    var recipeListLiveData: LiveData<List<Recipe>> = getRecipesUseCase.getRecipes()



//    TODO: убрать в другую вьюмодель
    fun addRecipe(inputName: String, inputText: String, inputPortions: String, inputIngredients: String, inputImage: String){
        val name = parseString(inputName)
        val text = parseString(inputText)
        val portions = parseNumber(inputPortions)
        val ingredients = parseString(inputIngredients)
        val image = parseString(inputImage)
        val category = Category("fruits", 1)
        if(validateInput(name, text, portions, ingredients, image)){
            val recipe = Recipe(
                name = name,
                text = text,
                portions = portions,
                ingredients = ingredients,
                image = image,
                category = category,
                id = null
            )
            addRecipeUseCase.addRecipe(recipe)
        }
    }

//    TODO: убрать
    fun addCategory(){
        val category = Category("fruits")
        addCategoryUseCase.addCategory(category)
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
        val errors: MutableList<Pair<String, String>> = mutableListOf()
        if(inputName.isEmpty()){
            errors.add(Pair("name", "Is empty"))
        }

        if(inputText.isEmpty()){
            errors.add(Pair("text", "Is empty"))
        }
        if(inputPortions <= 0 ){
            errors.add(Pair("portions", "Is not greater then 0"))
        }
        if(inputIngredients.isEmpty()){
            errors.add(Pair("ingredients", "Is Empty"))
        }
        if(inputImage.isEmpty()){
            errors.add(Pair("inputImage", "Is Empty"))
        }
        if(errors.isNotEmpty()){
            return false
        }
        return true
    }
}