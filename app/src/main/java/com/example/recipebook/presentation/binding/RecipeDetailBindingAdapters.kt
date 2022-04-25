package com.example.recipebook.presentation.binding

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.documentfile.provider.DocumentFile
import com.example.recipebook.R


@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Int){
    tv.text = number.toString()
}

@BindingAdapter("imageToImageView")
fun bindImageToImageView(iv: ImageView, imageUri: String){
    val uri: Uri = imageUri.toUri()

    if (DocumentFile.fromSingleUri(iv.context, uri)?.exists() == true) {
        iv.setImageURI(uri)
    }else{
        iv.setImageResource(R.drawable.image_blank)
    }
}