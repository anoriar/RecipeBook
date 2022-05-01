package com.example.recipebook.presentation.util.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import java.io.*
import java.util.*
import javax.inject.Inject

class ImageManager @Inject constructor(val context: Context) {
    companion object {
        const val DIR_NAME = "RecipeBook"
    }

    fun saveImageToExternalStorage(imageUri: Uri): String {
        val parcelFileDescriptor: ParcelFileDescriptor? = context.contentResolver.openFileDescriptor(imageUri, "r")
        val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
        val bitmap: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()

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