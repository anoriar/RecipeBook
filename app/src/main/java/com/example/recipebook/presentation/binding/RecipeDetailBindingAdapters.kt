package com.example.recipebook.presentation.binding

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.documentfile.provider.DocumentFile
import com.example.recipebook.R
import com.example.recipebook.presentation.util.ImageFromUri
import java.io.File


@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Int){
    tv.text = number.toString()
}

@BindingAdapter("imageToImageView")
fun bindImageToImageView(iv: ImageView, imageUri: String){
    ImageFromUri.setImageFromUri(iv, imageUri)
}