package com.example.recipebook.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipebook.R
import com.example.recipebook.databinding.FragmentRecipeAddEditBinding
import com.example.recipebook.databinding.FragmentRecipeDetailBinding
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.presentation.viewmodel.RecipeDetailViewModel
import javax.inject.Inject


class RecipeDetailFragment : Fragment() {

    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewModel


    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding:  FragmentRecipeDetailBinding
        get() {
            return _binding ?: throw RuntimeException("Binding can not be null")
        }

    private var recipeId: Int = UNDEFINED_INDEX

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().appModule(AppModule(requireActivity().application)).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeDetailViewModel.getRecipeById(recipeId)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = recipeDetailViewModel

    }


    private fun parseParams(){
        val args = requireArguments()
        if(!args.containsKey(RECIPE_ID_ARG)){
            throw RuntimeException("Param recipe id not found")
        }
        recipeId = args.getInt(RECIPE_ID_ARG, UNDEFINED_INDEX)
    }

    companion object {
        private const val RECIPE_ID_ARG = "recipe_id_arg"

        const val UNDEFINED_INDEX = 0

        fun getInstance(id: Int): RecipeDetailFragment{
            return RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_ID_ARG, id)
                }
            }
        }
    }
}