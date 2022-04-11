package com.example.recipebook.presentation.binding

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.recipebook.R

interface OnCategoryFilterButtonClickListener {
    fun onButtonClick(number: Int)
}

@BindingAdapter("categoryFilterButtonBackgroundColor")
fun bindCategoryFilterButtonBackgroundColor(btn: Button, isSelected: Boolean){
    if (isSelected) {
        val color = ContextCompat.getColor(btn.context, R.color.dark_green)
        btn.setBackgroundColor(color)
    }
}
