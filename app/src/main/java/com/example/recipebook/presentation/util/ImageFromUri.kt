package com.example.recipebook.presentation.util

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.recipebook.R
import java.io.File

class ImageFromUri {
    companion object {
        fun setImageFromUri(iv: ImageView, imageUri: String){
            val imgFile = File(imageUri)

            return if (imgFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                iv.setImageBitmap(bitmap)
            }else{
                iv.setImageResource(R.drawable.image_blank)
            }
        }
    }
}