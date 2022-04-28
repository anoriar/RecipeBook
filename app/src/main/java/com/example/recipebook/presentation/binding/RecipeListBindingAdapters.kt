package com.example.recipebook.presentation.binding

import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.recipebook.R


@BindingAdapter("categoryFilterButtonBackgroundColor")
fun bindCategoryFilterButtonBackgroundColor(btn: Button, isSelected: Boolean){
    val color = if (isSelected) {
        ContextCompat.getColor(btn.context, R.color.dark_green)
    }else{
        ContextCompat.getColor(btn.context, R.color.light_green)
    }
    btn.setBackgroundColor(color)
}

