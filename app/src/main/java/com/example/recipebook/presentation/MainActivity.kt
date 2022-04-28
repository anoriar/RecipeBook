package com.example.recipebook.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.recipebook.R
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.presentation.fragment.RecipeListFragment
import com.example.recipebook.presentation.util.permission.PermissionChecker
import com.example.recipebook.presentation.viewmodel.RecipeListViewModel
import com.example.recipebook.presentation.viewmodel.RecipeViewModel
import javax.inject.Inject

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
            .addToBackStack(null)
            .commit()
    }

    private fun checkReadExternalStoragePermission(){
        if(!PermissionChecker.checkPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)){
            permissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}