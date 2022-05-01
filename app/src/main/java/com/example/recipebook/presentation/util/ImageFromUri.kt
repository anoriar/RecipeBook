package com.example.recipebook.presentation.util

import android.Manifest
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.core.net.toUri
import com.example.recipebook.R
import com.example.recipebook.presentation.util.permission.PermissionChecker
import java.io.File

class ImageFromUri {
    companion object {
        fun setImageFromUri(iv: ImageView, imageUri: String){

            if(PermissionChecker.checkPermission(iv.context, Manifest.permission.READ_EXTERNAL_STORAGE)){
                val imgFile = File(imageUri)
                if (imgFile.exists() && PermissionChecker.checkPermission(iv.context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    iv.setImageBitmap(bitmap)
                }else{
                    try{
                        iv.context.contentResolver.openInputStream(imageUri.toUri())
                        iv.setImageURI(imageUri.toUri())
                    }catch(ex: Throwable){
                        iv.setImageResource(R.drawable.image_blank)
                    }
                }
            }else{
                iv.setImageResource(R.drawable.image_blank)
            }

        }
    }
}