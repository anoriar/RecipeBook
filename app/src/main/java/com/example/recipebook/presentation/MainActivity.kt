package com.example.recipebook.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.recipebook.R
import com.example.recipebook.presentation.fragment.RecipeListFragment
import com.example.recipebook.presentation.util.permission.PermissionChecker

class MainActivity : AppCompatActivity() {

    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkReadExternalStoragePermission()
        launchFragment()
    }

    private fun launchFragment(){
        supportFragmentManager.beginTransaction().
        replace(R.id.recipe_book_container, RecipeListFragment.getInstance())
            .commit()
    }

    private fun checkReadExternalStoragePermission(){
        if(!PermissionChecker.checkPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)){
            permissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}