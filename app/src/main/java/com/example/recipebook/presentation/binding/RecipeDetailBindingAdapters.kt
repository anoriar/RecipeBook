package com.example.recipebook.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Int){
    tv.text = number.toString()
}