package com.example.recipebook.presentation.util

import android.Manifest
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.recipebook.R
import com.example.recipebook.presentation.util.permission.PermissionChecker
import java.io.File

class ImageFromUri {
    companion object {
        fun setImageFromUri(iv: ImageView, imageUri: String){
            val imgFile = File(imageUri)

            return if (imgFile.exists() && PermissionChecker.checkPermission(iv.context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                iv.setImageBitmap(bitmap)
            }else{
                iv.setImageResource(R.drawable.image_blank)
            }
        }
    }
}