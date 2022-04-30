package com.example.recipebook.presentation.fragment

import android.Manifest
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.recipebook.R
import com.example.recipebook.databinding.FragmentRecipeAddEditBinding
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.presentation.adapter.CategorySpinnerAdapter
import com.example.recipebook.presentation.util.FragmentNavEnum
import com.example.recipebook.presentation.util.permission.PermissionChecker
import com.example.recipebook.presentation.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream
import java.util.*
import javax.inject.Inject


class RecipeAddEditFragment : Fragment() {

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    private lateinit var spinnerAdapter: CategorySpinnerAdapter

    private lateinit var onSaveCallback: ((name: String, text: String, portions: String, ingredients: String, category: Category) -> Unit)

    private var mode: String = UNKNOWN_MODE
    private var recipeId: Int = UNDEFINED_INDEX

    private var _binding: FragmentRecipeAddEditBinding? = null
    private val binding: FragmentRecipeAddEditBinding
        get() {
            return _binding ?: throw RuntimeException("Binding can not be null")
        }

    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            isWriteExternalStoragePermitted = true
        }
    }

    private val fileChooserContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (!isWriteExternalStoragePermitted){
            permissionLauncher.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (it != null && isWriteExternalStoragePermitted) {
            recipeViewModel.changeImage(it)
        }
    }

    private var isWriteExternalStoragePermitted: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().appModule(AppModule(requireActivity().application)).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWriteExternalStoragePermitted = PermissionChecker.checkPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        parseParams()
        setHasOptionsMenu(true)
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
        initSaveBtn()
        observeViewModel()
        launchInRightMode()
    }

    private fun observeViewModel(){
        val validationFields: Map<String, EditText> = mapOfValidationFields()

        recipeViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.values = it
            spinnerAdapter.notifyDataSetChanged()
        }

        recipeViewModel.recipe.observe(viewLifecycleOwner) {
            binding.etRecipeName.setText(it.name)
            binding.etRecipeText.setText(it.text)
            binding.etRecipeIngredients.setText(it.ingredients)
            binding.etRecipePortions.setText(it.portions.toString())
        }

        recipeViewModel.categorySpinnerPairData.observe(viewLifecycleOwner) {
            binding.spinnerRecipeCategory.setSelection(spinnerAdapter.getPosition(it))
        }

        recipeViewModel.errors.observe(viewLifecycleOwner) {
            for (error in it) {
                validationFields[error.first]?.error = getString(error.second)
            }
        }

        recipeViewModel.recipeImage.observe(viewLifecycleOwner) {
            binding.ivRecipeImage.setImageURI(it)
        }
    }

    private fun initCategoriesSpinner(){
        spinnerAdapter = CategorySpinnerAdapter(requireActivity(), R.layout.spinner_item)
        binding.spinnerRecipeCategory.adapter = spinnerAdapter
    }

    private fun initRecipeImageIv(){
        binding.ivRecipeImage.setOnClickListener {
            fileChooserContract.launch("image/*")
        }
    }

    private fun initSaveBtn(){
        binding.btnSaveRecipe.setOnClickListener {
            onSaveCallback(
                binding.etRecipeName.text.toString(),
                binding.etRecipeText.text.toString(),
                binding.etRecipePortions.text.toString(),
                binding.etRecipeIngredients.text.toString(),
                binding.spinnerRecipeCategory.selectedItem as Category
            )
        }
    }


    private fun launchInRightMode(){
        when(mode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }
    }

    private fun launchAddMode(){
        onSaveCallback = {
            name: String, text: String, portions: String, ingredients: String, category: Category ->
            recipeViewModel.addRecipe(
                inputName = name,
                inputText = text,
                inputPortions = portions,
                inputIngredients = ingredients,
                inputCategory = category
            )
            requireActivity().supportFragmentManager.popBackStack()
            showActionMessage(R.string.recipe_added)
        }
    }

    private fun launchEditMode(){
        recipeViewModel.initRecipeById(recipeId)
        onSaveCallback = {
                name: String, text: String, portions: String, ingredients: String, category: Category ->
            recipeViewModel.updateRecipe(
                id = recipeId,
                inputName = name,
                inputText = text,
                inputPortions = portions,
                inputIngredients = ingredients,
                inputCategory = category
            )
            requireActivity().supportFragmentManager.popBackStack(FragmentNavEnum.RECIPE_DETAIL_FRAGMENT.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            showActionMessage(R.string.recipe_edited)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(mode == EDIT_MODE){
            inflater.inflate(R.menu.recipe_edit_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(mode == EDIT_MODE){
            return when(item.itemId) {
                R.id.delete_recipe_menu_action -> {
                    deleteRecipe()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteRecipe(){
        recipeViewModel.deleteRecipe()
        requireActivity().supportFragmentManager.popBackStack(FragmentNavEnum.RECIPE_DETAIL_FRAGMENT.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        showActionMessage(R.string.recipe_deleted)
    }

    private fun showActionMessage(resId: Int){
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            resId,
            Snackbar.LENGTH_SHORT
        ).show()
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