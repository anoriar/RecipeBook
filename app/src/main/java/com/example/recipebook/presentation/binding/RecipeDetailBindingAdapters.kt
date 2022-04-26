package com.example.recipebook.presentation.binding

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.documentfile.provider.DocumentFile
import com.example.recipebook.R
import java.io.File


@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Int){
    tv.text = number.toString()
}

@BindingAdapter("imageToImageView")
fun bindImageToImageView(iv: ImageView, imageUri: String){
    val imgFile = File(imageUri)

    if (imgFile.exists()) {
        val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        iv.setImageBitmap(bitmap)
    }else{
        iv.setImageResource(R.drawable.image_blank)
    }


//    val uri: Uri = imageUri.toUri()
//
//    if (DocumentFile.fromSingleUri(iv.context, uri)?.exists() == true) {
//        iv.setImageURI(uri)
//
}