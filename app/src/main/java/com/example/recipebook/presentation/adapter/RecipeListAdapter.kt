package com.example.recipebook.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.databinding.RecipeListItemBinding
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.presentation.adapter_callback.RecipeDiffCallback


class RecipeListAdapter: ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(
    RecipeDiffCallback()
) {
    var onRecipeClickListener: ((Recipe) -> Unit)? = null
    var onFavoriteClickListener: ((Recipe, Boolean) -> Unit)? = null

    class RecipeViewHolder(val recipeListItemBinding: RecipeListItemBinding) : RecyclerView.ViewHolder(recipeListItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe: Recipe = getItem(position)
        holder.recipeListItemBinding.recipe = recipe
        holder.itemView.setOnClickListener {
            onRecipeClickListener?.invoke(recipe)
        }

        holder.recipeListItemBinding.checkboxFav.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton?,
                isChecked: Boolean
            ) {
                onFavoriteClickListener?.invoke(recipe, isChecked)
            }
        })
    }
}