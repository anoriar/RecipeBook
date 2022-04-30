package com.example.recipebook.presentation.util.media

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

class ImageManager @Inject constructor(val context: Context) {
    companion object {
        const val DIR_NAME = "RecipeBook"
    }

    fun saveImageToExternalStorage(bitmap: Bitmap): String {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val dir: File = File(path + "/${DIR_NAME}")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        return file.absolutePath
    }

    fun removeImageFromExternalStorage(filePath: String): Boolean {
        val file: File = File(filePath)
        return file.delete()
    }
}