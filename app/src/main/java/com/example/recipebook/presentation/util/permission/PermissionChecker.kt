package com.example.recipebook.presentation.util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionChecker {

    companion object {
        fun checkPermission(context: Context, permission: String): Boolean{
            return when (ContextCompat.checkSelfPermission(context, permission)) {
                PackageManager.PERMISSION_GRANTED -> true
                else -> false
            }
        }
    }
}