package com.example.recipebook.presentation.fragment

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.recipebook.databinding.FragmentRecipeAddEditBinding
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.presentation.adapter.CategorySpinnerAdapter
import com.example.recipebook.presentation.viewmodel.RecipeViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URI
import java.util.*
import javax.inject.Inject

class RecipeAddEditFragment : Fragment() {

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    private lateinit var spinnerAdapter: CategorySpinnerAdapter

    private var mode: String = UNKNOWN_MODE
    private var recipeId: Int = UNDEFINED_INDEX

    private var _binding: FragmentRecipeAddEditBinding? = null
    private val binding: FragmentRecipeAddEditBinding
        get() {
            return _binding ?: throw RuntimeException("Binding can not be null")
        }

    private var isWritePermissionGranted: Boolean = false

    private val permissionLauncher: ActivityResultLauncher<Array<String>> = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        isWritePermissionGranted = it[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
    }

    private val fileChooserContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            recipeViewModel.setImageUri(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().appModule(AppModule(requireActivity().application)).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    private fun parseParams(){
        val args = requireArguments()
        if (!args.containsKey(MODE_ARG)) {
            throw RuntimeException("Param mode not found")
        }
        mode = args.getString(MODE_ARG) ?: UNKNOWN_MODE
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown mode $mode")
        }

        if(mode == EDIT_MODE) {
            if (!args.containsKey(RECIPE_ID_ARG)) {
                throw RuntimeException("Param recipe id not found")
            }
            recipeId = args.getInt(RECIPE_ID_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoriesSpinner()
        initRecipeImageIv()
        observeViewModel()
        launchInRightMode()
    }

    private fun observeViewModel(){
        val validationFields: Map<String, EditText> = mapOfValidationFields()

        recipeViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.values = it
            spinnerAdapter.notifyDataSetChanged()
        }

        recipeViewModel.errors.observe(viewLifecycleOwner) {
            for (error in it) {
                validationFields[error.first]?.error = getString(error.second)
            }
        }

        recipeViewModel.shouldClose.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        recipeViewModel.recipeImage.observe(viewLifecycleOwner) {
            binding.ivRecipeImage.setImageURI(it)
        }
    }

    private fun initCategoriesSpinner(){
        spinnerAdapter = CategorySpinnerAdapter(requireActivity(), android.R.layout.simple_spinner_item)
        binding.spinnerRecipeCategory.adapter = spinnerAdapter
    }

    private fun initRecipeImageIv(){
        binding.ivRecipeImage.setOnClickListener {
            fileChooserContract.launch("image/*")
        }
    }


    private fun launchInRightMode(){
        when(mode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }
    }

    private fun launchAddMode(){
        val drawable = binding.ivRecipeImage.drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        val uri: Uri = saveImageToExternalStorage(bitmap)
        binding.btnSaveRecipe.setOnClickListener {
            recipeViewModel.addRecipe(
                binding.etRecipeName.text.toString(),
                binding.etRecipeText.text.toString(),
                binding.etRecipePortions.text.toString(),
                binding.etRecipeIngredients.text.toString(),
//                TODO: изображения
                uri.toString(),
//                TODO:??
                binding.spinnerRecipeCategory.selectedItem as Category
            )
        }
    }

//    TODO: доделать
    private fun launchEditMode(){
        recipeViewModel.getRecipeById(recipeId)
        binding.btnSaveRecipe.setOnClickListener {
//            recipeViewModel.updateRecipe(recipeId)
        }
    }

    /**
     * Map ViewModel schema with views
     */
    private fun mapOfValidationFields() = mapOf(
        RecipeViewModel.NAME_IS_EMPTY.first to binding.etRecipeName,
        RecipeViewModel.TEXT_IS_EMPTY.first to binding.etRecipeText,
        RecipeViewModel.INGREDIENTS_IS_EMPTY.first to binding.etRecipeIngredients,
        RecipeViewModel.PORTIONS_INVALID_FORMAT.first to binding.etRecipePortions
    )


    // Method to save an image to external storage
    private fun saveImageToExternalStorage(bitmap:Bitmap):Uri{
        val path = Environment.getExternalStorageDirectory().toString()
        val file = File(path, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }
    companion object {
        private const val MODE_ARG = "mode_arg"
        private const val RECIPE_ID_ARG = "recipe_id_arg"

        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        const val UNKNOWN_MODE = "unknown_mode"

        const val UNDEFINED_INDEX = 0

        fun getAddInstance(): RecipeAddEditFragment{
            return RecipeAddEditFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_ARG, ADD_MODE)
                }
            }
        }

        fun getEditInstance(recipeId: Int): RecipeAddEditFragment{
            return RecipeAddEditFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_ARG, EDIT_MODE)
                    putInt(RECIPE_ID_ARG, recipeId)
                }
            }
        }
    }
}